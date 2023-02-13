
async function Validator(options) {
    // Tìm form-group element
    function getParent(element, selector) {
        while (element.parentElement) {
            if (element.parentElement.matches(selector)) {
                return element.parentElement;
            }
            element = element.parentElement;
        }
    }

    //  Lưu tất cả rules của các trường cần validate
    let selectorRules = {};

    // Hàm validate
    async function validate(inputElement, rule) {
        // Lấy thẻ hiện thông báo lỗi
        let errorElement = getParent(
            inputElement,
            options.formGroupSelector,
        ).querySelector(options.errorSelector);

        //Khai báo thông biến chứa thông báo lỗi
        let errorMessage; //= rule.test(inputElement.value);

        // Rule của trường hiện tại cần validate
        let rules = selectorRules[rule.selector];

        // Lập qua từng rule để kiểm tra 
        for (let i = 0; i < rules.length; i++) {
            // Kiểm tra tùy theo kiểu của trường dữ liệu (radio, checkbox, input)
            switch (inputElement.type) {
                case 'radio':
                case 'checkbox':
                    errorMessage = rules[i](
                        formElement.querySelector(rule.selector + ':checked'),
                    )
                    break;
                default:
                    let result = await rules[i](inputElement.value);
                    errorMessage = result;
                    break;
            }
            //Nếu có lõi thì thoát khỏi vòng lập
            if (errorMessage) break;
        }

        // Nếu có lỗi thì hiện thông báo lỗi và thêm class invalid 
        if (errorMessage) {
            errorElement.innerText = errorMessage;
            getParent(inputElement, options.formGroupSelector).classList.add(
                options.invalidSelector
            );
        }
        // Nếu không còn lỗi thì xóa thông báo lỗi và xóa class invalid
        else {
            errorElement.innerText = '';
            getParent(inputElement, options.formGroupSelector).classList.remove(
                options.invalidSelector
            );
        }
        // Nếu có lỗi thì return false, không có lỗi thì return true
        return !errorMessage;
    }
    //Lấy element form cần validate
    let formElement = document.querySelector(options.form);

    //Nếu form tồn tại
    if (formElement) {
        // Xử lý sự kiện submit của form
        formElement.onsubmit = function(e) {
            //Xóa bỏ sự kiện submit mặc định của form
            e.preventDefault();
            //Mặc định form là không có lỗi
            let isFormValid = true;
            options.rules.forEach(async function(rule) {
                //Lấy input từ selector
                let inputElement = formElement.querySelector(rule.selector);
                //Validate lại trường vừa lấy
                let isValid = await validate(inputElement, rule);
                //Nếu có một lỗi thì cho form bị lỗi
                if (!isValid) {
                    isFormValid = false;
                    //  break;
                }
            });
            //Nếu from không hợp lệ thì kiểm tra xem có đặt phương thức khi form validate thành công hay không
            if (isFormValid) {
                //Nếu có đặt phương thức xử lý thì lấy ra dữ liệu nhập vào từ tất cả các input, checkbox, ... không bị disabled
                if (typeof options.onSubmit === 'function') {
                    let enableInputs = formElement.querySelectorAll(
                        '[name]:not([disabled])',
                    );
                    //Lập qua các nodeElement
                    let formValues = Array.from(enableInputs).reduce(function(
                        values,
                        input,
                    ) {
                        switch (input.type) {
                            case 'radio':
                                values[input.name] = formElement.querySelector(
                                    'input[name="' + input.name + '"]:checked',
                                ).value;
                                break;
                            case 'checkbox':
                                if (input.matches(':checked')) {
                                    values[input.name] = [];
                                    return values;
                                }

                                if (!Array.isArray(values[input.name])) {
                                    values[input.name] = [];
                                }
                                values[input.name].push(values[input.value]);
                                break;
                            case 'file':
                                values[input.name] = input.files;
                                break;
                            default:
                                values[input.name] = input.value;
                                break;
                        }
                        return values;
                    }, {});
                    options.onSubmit(formValues);
                } else {
                    formElement.submit();
                }
            }
        }

        options.rules.forEach(function(rule) {
            if (Array.isArray(selectorRules[rule.selector])) {
                selectorRules[rule.selector].push(rule.test);
            } else {
                selectorRules[rule.selector] = [rule.test];
            }

            let inputElements = formElement.querySelectorAll(rule.selector);
            Array.from(inputElements).forEach(function(inputElement) {
                if (inputElement) {
                    inputElement.onblur = async function() {
                        await validate(inputElement, rule);
                    }

                    inputElement.oninput = function() {
                        let errorElement = getParent(
                            inputElement,
                            options.formGroupSelector,
                        ).querySelector(options.errorSelector);
                        errorElement.innerText = '';
                        getParent(inputElement, options.formGroupSelector).classList.remove(
                            options.invalidSelector
                        );
                    };

                    inputElement.onchange = function(e) {
                        let errorElement = getParent(
                            inputElement,
                            options.formGroupSelector,
                        ).querySelector(options.errorSelector);
                        errorElement.innerText = '';
                        getParent(inputElement, options.formGroupSelector).classList.remove(
                            options.invalidSelector
                        );
                    }
                }
            });
        });
    }
}

Validator.isRequired = function(selector, message) {
    return {
        selector: selector,
        test: function(value) {
            return value ? undefined : message || 'Vui lòng nhập trường này!'
        },
    };
}

Validator.isEmail = function(selector, message) {
    return {
        selector: selector,
        test: function(value) {
            let regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
            return regex.test(value) ?
                undefined :
                message || 'Trường này phải là email'
        },
    };
}

Validator.minLength = function(selector, min, message) {
    return {
        selector: selector,
        test: function(value) {
            return value.trim().length >= min ?
                undefined :
                message || 'Vui lòng nhập tối thiểu ' + min + ' ký tự!'
        },
    };
}

Validator.isEquals = function(selector, getConfirmValue, message) {
    return {
        selector: selector,
        test: function(value) {
            return value === getConfirmValue() ?
                undefined :
                message || 'Giá trị nhập vào không chính xác!'
        },
    };
}

Validator.maxLength = function(selector, max, message) {
    return {
        selector: selector,
        test: function(value) {
            return value.trim().length <= max ? undefined : message || 'Vui lòng nhập ít hơn ' + max + ' ký tự!';
        }
    }
}

Validator.isPhoneNumber = function(selector, message) {
    return {
        selector: selector,
        test: function(value) {
            let regex = /(([03+[2-9]|05+[6|8|9]|07+[0|6|7|8|9]|08+[1-9]|09+[1-4|6-9]]){3})+[0-9]{7}\b/g;
            return regex.test(value) ? undefined : message || 'Số điện thoại sai định dạng!';
        }
    };
}

Validator.isNumber = function(selector, message) {
    return {
        selector: selector,
        test: function(value) {
            return !isNaN(value) ? undefined : message || 'Trường này phải là số!';
        }
    };
}

Validator.isInteger = function(selector, message) {
    return {
        selector: selector,
        test: function(value) {
            return Number.isInteger(Number(value.trim())) ? undefined : message || 'Vui lòng nhập số nguyên!';
        }
    };
}

Validator.maxValue = function(selector, maxValue, message) {
    return {
        selector: selector,
        test: function(value) {
            return Number(value.trim()) <= maxValue ? undefined : message || 'Vui lòng nhập giá trị tối đa là ' + maxValue + '!';
        }
    };
}


Validator.minValue = function(selector, minValue, message) {
    return {
        selector: selector,
        test: function(value) {
            return Number(value.trim()) >= (typeof minValue === 'function' ? minValue() : minValue) ? undefined : message || 'Vui lòng nhập giá trị tối thiểu là ' + minValue + '!';
        }
    };
}

Validator.isString = function(selector, message) {
    return {
        selector: selector,
        test: function(value) {
            return typeof value === "string" ? undefined : message || 'Vui lòng nhập chuỗi!';
        }
    };
}

Validator.isNotContainsSymbol = function(selector, message) {
    return {
        selector: selector,
        test: function(value) {
            let regex = /[ `!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;
            return !regex.test(value) ? undefined : message || 'Chuỗi không được chứa ký tự đặc biệt (kể cả khoảng trắng)!';
        }
    };
}

Validator.isImageUrl = function(selector, message) {
    return {
        selector: selector,
        test: function(value) {
            let regex = /\.(jpg|jpeg|png|webp|avif|gif|svg)$/;
            return regex.test(value) ? undefined : message || 'Vui lòng chọn tệp là hình ảnh!';
        }
    };
}

Validator.customRule = function(selector, func, message) {
    return {
        selector: selector,
        test: async function(value) {
            let result = await func(value);
            return result ? undefined : message || 'Không được trùng!';
        }
    };
}