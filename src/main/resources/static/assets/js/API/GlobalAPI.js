class GlobalAPI {
    static $http = angular.injector(["ng"]).get("$http");
    static rootUrl = '/api';
    url;
    static namespace = {
		AUTHORITY: 'authorities',
        ACTION: 'actions',
        ADDRESS: 'addresses',
        BCOIN_HISTORY: 'bcoinhistories',
        BSERVICE: 'bservices',
        BSERVICE_HISTORY: 'bservicehistories',
        BSERVICE_PRICE: 'bserviceprices',
        BSERVICE_SUBBANNER: 'bservicesubbanner',
        CARD: 'cards',
        CARD_SUBBANNER: 'cardsubbanners',
        CART: 'carts',
        CATEGORY: 'categories',
        COMMENT: 'comments',
        DISPLAYFEE_HISTORY: 'displayfeehistories',
        DISPLAYFEE: 'displayfees',
        FAIL_HISTORY: 'failhistories',
        FOLLOW_SHOP: 'followshops',
        HIDECARD: 'hidecards',
        LOVECARD: 'lovecards',
        MYBCOIN: 'mybcoins',
        MYBSERVICE: 'mybservices',
        MYSHOP: 'shops',
        NEWS: 'news',
        ORDERDETAIL: 'orderdetails',
        ORDER: 'orders',
        POST: 'posts',
        PROBLEM: 'problems',
        PRODUCT: 'products',
        PRODUCT_HISTORY: 'producthistories',
        REGISTE_RACTIVE: 'registeractives',
        REPCOMMENT: 'repcomments',
        REPORT: 'reports',
        ROLE: 'roles',
        SHARE: 'shares',
        STATUS: 'status',
        USER: 'users',
        REVIEW: 'reviews',
        REP_REVIEW: 'repreviews',
        MAIL: 'sendmail',
        UPLOAD: 'upload',
        USERSCHEMA: 'userschemas',
        CHAT_ROOM: 'chatrooms',
        MESSAGES: 'messages'
    };


    setNamespace(np) {
        this.url = `${GlobalAPI.rootUrl}/${np}`;
    }


    request(method = "GET", url = undefined, data = undefined) {
        let token = getCookie("_biglobby_sesstoken_");
        let req = {
            method: method,
            url: url,
            headers: {
                'Authorization': `Bearer ${token}`,
                "Access-Control-Allow-Methods": "GET, PUT, POST, DELETE"
            },
            data: data
        };
        if (!token) {
            delete req.headers['Authorization'];
        }
        if (method === "GET" || method === "DELETE") {
            delete req.data;
        }
        return GlobalAPI.$http(req);
    }


    static async uploadFile(folder, data) {
        const result = await GlobalAPI.$http({
            method: 'POST',
            url: `${GlobalAPI.rootUrl}/upload/${folder}`,
            headers: {
                'Content-Type': undefined // 'multipart/form-data'
            },
            data: data,
            // transformRequest: function(data, headersGetter) {
            //     var formData = new FormData();
            //     angular.forEach(data, function(value, key) {
            //         formData.append(key, value);
            //     });
            //     var headers = headersGetter();
            //     delete headers['Content-Type'];
            //     return formData;
            // }
        }).catch((err) => {
            throw err;
        });
        return result;
    }


    static async login(data) {
        const result = await GlobalAPI.$http.post(`/authentication/login`, data).then(res => {
            window.setCookie('_biglobby_sesstoken_', res.headers("Authorization"), (1 / 24));
            return res.data;
        }).catch((err) => {
            throw err;
        })
        return result;
    }

    static async encryptUserData(data) {
        const encrypted = await GlobalAPI.$http.post(`/authentication/encrypt`, data).then(res => {
            return res.data;
        }).catch((err) => {
            console.log(err);
            throw err;
        })
        return encrypted;
    }

    static async validateUserData(data) {
        const user = await GlobalAPI.$http.post(`/authentication/validate`, data).then(res => {
            window.setCookie('_biglobby_sesstoken_', res.headers("Authorization"), (1 / 24));
            return res.data;
        }).catch((err) => {
            console.log(err);
            throw err;
        })
        return user;
    }

    async getById(id) {
        let entity = await this.request("GET", `${this.url}/${id}`).then((res) => {
            return res.data;
        }).catch((err) => {
            throw err;
        });
        return entity;
    }

    async getPage(pagenum, limit) {
        let page = await this.request("GET", `${this.url}?page=${pagenum}&limit=${limit}`).then((res) => {
            return res.data;
        }).catch((err) => {
            throw err;
        });
        return page;
    }

    async searchByKeyword(keyword, pagenum, limit) {
        let page = await this.request("GET", `${this.url}?keyword=${keyword}&page=${pagenum}&limit=${limit}`).then((res) => {
            return res.data;
        }).catch((err) => {
            throw err;
        });
        return page;
    }

    async getAll() {
        let entities = await this.request("GET", this.url).then((res) => {
            return res.data;
        }).catch((err) => {
            throw err;
        });
        return entities;
    }

    async create(data) {
        let entity = await this.request("POST", this.url, data).then((res) => {
            return res.data;
        }).catch((err) => {
            throw err;
        });
        return entity;
    }

    async update(id, data) {
        let entity = await this.request("PUT", `${this.url}/${id}`, data).then((res) => {
            return res.data;
        }).catch((err) => {
            throw err;
        });
        return entity;
    }


    async putRequest(id, path, data) {
        let entity = await this.request("PUT", `${this.url}/${id}/${path}`, data).then((res) => {
            return res.data;
        }).catch((err) => {
            throw err;
        });
        return entity;
    }



    async delete(id) {
        let success = await this.request('DELETE', `${this.url}/${id}`).then((res) => {
            if (res.status === 204) {
                return true;
            }
            return false;
        }).catch((err) => {
            throw err;
        });
        return success;
    }


    async deleteCollection(id, collectionName) {
        let result = await this.request('DELETE', `${this.url}/${id}/${collectionName}`).then((res) => {
            return res.data;
        }).catch((err) => {
            throw err;
        });
        return result;
    }



    async getPageCollections(id, collectionsName, page, limit) {
        let collections = await this.request("GET", `${this.url}/${id}/${collectionsName}?page=${page}&limit=${limit}`).then((res) => {
            return res.data;
        }).catch((err) => {
            if (err.status !== 404) {
                throw err;
            }
        });
        return collections;
    }

    async getCollections(id, collectionsName) {
        let collections = await this.request("GET", `${this.url}/${id}/${collectionsName}`).then((res) => {
            return res.data;
        }).catch((err) => {
            throw err;
        });
        return collections;
    }

    async getByProperty(name, value) {
        let result = await this.request('GET', `${this.url}?${name}=${value}`).then((res) => {
            console.log(res);
            return res.data;
        }).catch((err) => {
            return null;
        });
        return result;
    }

    async getCountOfCollection(id, collection) {
        let result = await this.request("GET", `${this.url}/${id}?countof=${collection}`).then((res) => {
            return res.data;
        }).catch((err) => {
            return null;
        });
        return result;
    }

    async getByProperties(data) {
        let query = '';
        const keys = Object.keys(data);
        let keyLength = keys.length;
        for (let i = 0; i < keyLength; i++) {
            if (i === 0) {
                query += `?${keys[i]}=${data[keys[i]]}`;
            } else {
                query += `&${keys[i]}=${data[keys[i]]}`;
            }
        }
        let result = await this.request('GET', `${this.url}${query}`).then((res) => {
            return res.data;
        }).catch((err) => {
            return null;
        });
        return result;
    }



    async getPageByPropertiesAndFilter(pagenum, limit, propperties, filter) {
        let query = '';
        if (Object.keys(propperties).length > 0) {
            const keys = Object.keys(propperties);
            let keyLength = keys.length;
            for (let i = 0; i < keyLength; i++) {
                query += `&${keys[i]}=${propperties[keys[i]]}`;
            }
        }
        let page = await this.request("POST", `${this.url}?page=${pagenum}&limit=${limit}${query}`, filter).then((res) => {
            return res.data;
        }).catch((err) => {
            throw err;
        })
        return page;
    }


    async getPageByProperties(pagenum, limit, propperties) {
        let query = '';
        const keys = Object.keys(propperties);
        let keyLength = keys.length;
        for (let i = 0; i < keyLength; i++) {
            query += `&${keys[i]}=${propperties[keys[i]]}`;
        }
        let page = await this.request("GET", `${this.url}?page=${pagenum}&limit=${limit}${query}`).then((res) => {
            return res.data;
        }).catch((err) => {
            console.log(err)
                //throw err;
        })
        return page;
    }

    /**by Truongnvn */
    //I. Product admin
    //1. get Page PrductAdmin
    ///admin/page/{Npage}/{Nitem}/{isProduct}/{status}
    // async getPageProductAdmin(Npage, Nitem, isProduct, status, key) {
    //   let page = await this.$http.get(`${this.url}/admin/page/${Npage}/${Nitem}/${isProduct}/${status}?key=${key}`).then((res) => {
    //       return res.data;
    //    }).catch((err) => {
    //       console.error(err);
    //      throw err;
    //   });
    //   return page;
    //}

    static async uploadFile(folder, data) {
        const result = await GlobalAPI.$http({
            method: 'POST',
            url: `${GlobalAPI.rootUrl}/upload/${folder}`,

            headers: {
                'Content-Type': undefined

            },
            data: data,
        }).catch((err) => {
            throw err;
        })
        return result;
    }

    //UPLOAD IMAGE
    static async uploadFile(folder, data) {
        const result = await GlobalAPI.$http({
            method: 'POST',
            url: `${GlobalAPI.rootUrl}/upload/${folder}`,

            headers: {
                'Content-Type': undefined

            },
            data: data,
        }).catch((err) => {
            throw err;
        })
        return result;
    }

    // //2. getFailCard
    //   async getFailHistoryCard(id) {
    //     let data = await this.$http.get(`${this.url}/card/${id}`).then((res) => {
    //         return res.data;
    //     }).catch((err) => {
    //         console.error(err);
    //         throw err;
    //     });
    //     return data;
    // }
    // //3. get Page Product Report Admin
    // //reports/admin/page/" + NPage + "/" + Nitem + "/" + $scope.listActive +"?key="+ $scope.searchtext
    //  async getPageProductReportAdmin(Npage, Nitem, status, key) {
    //     let page = await this.$http.get(`${this.url}/reports/admin/page/${Npage}/${Nitem}/${status}?key=${key}`).then((res) => {
    //         return res.data;
    //     }).catch((err) => {
    //         console.error(err);
    //         throw err;
    //     });
    //     return page;
    // }
    // //II. USER
    // //biglobby/products/user/page/" + NPage + "/" + Nitem +"/"+ $scope.listActive +"?key="+ $scope.searchtext + "&user="  +  JSON.parse(sessionStorage.usersession)
    // //1. cua hang
    //  async getPageProductUserShop(Npage, Nitem, status, key, user) {
    //     let page = await this.$http.get(`${this.url}/user/page/${Npage}/${Nitem}/${status}?key=${key}&user=${user}`).then((res) => {
    //         return res.data;
    //     }).catch((err) => {
    //         console.error(err);
    //         throw err;
    //     });
    //     return page;
    // }
    // ///biglobby/products/user/cuahang/page/${$scope.NPage}/${$scope.Nitem}/${$scope.listActive}`, $scope.obj_filt
    // async getPageProductUserShopFilter(Npage, Nitem, status,fillter) {
    //     let page = await this.$http.post(`${this.url}/user/cuahang/page/${Npage}/${Nitem}/${status}`,fillter).then((res) => {
    //         return res.data;
    //     }).catch((err) => {
    //         console.error(err);
    //         throw err;
    //     });
    //     return page;
    // }
    // //
    //  async getLoveCard(user,card) {
    //     let lovecard = await this.$http.get(`${this.url}?user.id=${user}&card.id=${card}`).then((res) => {
    //         return res.data;
    //     }).catch((err) => {
    //         console.error(err);
    //         throw err;
    //     });
    //     return lovecard;
    // }
    // //
    // async getUrlTemp(urltemp,name,value) {
    //     let lovecard = await this.$http.get(`${this.url}/${urltemp}/?${name}=${value}`).then((res) => {
    //         return res.data;
    //     }).catch((err) => {
    //         console.error(err);
    //         throw err;
    //     });
    //     return lovecard;
    // }
    // //
    // async postOrderDT(collection, data) {
    // 	let page = await this.$http.post(`${this.url}${collection}`, data).then((res) => {
    // 		return res.data;
    // 	}).catch((err) => {
    // 		console.error(err);
    // 		throw err;
    // 	});
    // 	return page;
    // }

    /**by Truongnvn */
}