function Validator(formSelector) {
    let formRules = {};

    let validatorRules = {
        required: function(value, message) {
            let defaultMessage = 'Vui lòng nhập trường này!';
            if (value) {
                return value.trim() ? undefined : message || defaultMessage;
            }
            return message || defaultMessage;
        },
        email: function(value, message) {
            let regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
            return regex.test(value.trim()) ? undefined : message || 'Trường này phải là email!';
        },
        min: function(min, message) {
            return function() {

            }
            return value.trim().length >= min ? undefined : message || 'Vui lòng nhập ít nhất ' + min + ' ký tự!';
        }

    }




    let formElement = document.querySelector(formSelector);

    if (formElement) {
        let inputs = formElement.querySelectorAll('[name][rules]');
        for (let input of inputs) {
            formRules[input.name] = input.getAttribute('rules');
        }
    }
}