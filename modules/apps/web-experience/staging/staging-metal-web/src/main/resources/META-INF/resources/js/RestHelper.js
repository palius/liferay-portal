import Ajax from 'metal-ajax';

class RestHelper {
    /**
     * Standard GET request.
     * 
     * @static
     * @param {string} url - The requests URL.
     * @param {RequestOptions} [requestOptions={}] - The requests options.
     * @returns {Promise} - Deferred ajax request.
     * @memberof RestHelper
     */
    static get(url, requestOptions = {}) {
        requestOptions = {
            body: requestOptions.body || null,
            opt_headers: requestOptions.opt_headers || null,
            opt_params: requestOptions.opt_params || null,
            opt_timeout: requestOptions.opt_timeout || null,
            opt_sync: requestOptions.opt_sync || false,
            opt_withCredentials: requestOptions.opt_withCredentials || false,
            opt_callback: requestOptions.opt_callback || null
        };

        return RestHelper.request(url, "get", requestOptions);
    }

    /**
     * Standard POST request.
     * 
     * @static
     * @param {string} url - The requests URL.
     * @param {RequestOptions} [requestOptions={}] - The requests options.
     * @returns {Promise} - Deferred ajax request.
     * @memberof RestHelper
     */
    static post(url, requestOptions = {}) {
        requestOptions = {
            body: requestOptions.body || null,
            opt_headers: requestOptions.opt_headers || null,
            opt_params: requestOptions.opt_params || null,
            opt_timeout: requestOptions.opt_timeout || null,
            opt_sync: requestOptions.opt_sync || false,
            opt_withCredentials: requestOptions.opt_withCredentials || false,
            opt_callback: requestOptions.opt_callback || null
        };
        
        return RestHelper.request(url, "post", requestOptions);
    }
    
    /**
     * Standard PUT request.
     * 
     * @static
     * @param {string} url - The requests URL.
     * @param {RequestOptions} [requestOptions={}] - The requests options.
     * @returns {Promise} - Deferred ajax request.
     * @memberof RestHelper
     */
    static put(url, requestOptions = {}) {
        requestOptions = {
            body: requestOptions.body || null,
            opt_headers: requestOptions.opt_headers || null,
            opt_params: requestOptions.opt_params || null,
            opt_timeout: requestOptions.opt_timeout || null,
            opt_sync: requestOptions.opt_sync || false,
            opt_withCredentials: requestOptions.opt_withCredentials || false,
            opt_callback: requestOptions.opt_callback || null
        };

        return RestHelper.request(url, "put", requestOptions);
    }
    
    /**
     * Standard DELETE request.
     * 
     * @static
     * @param {string} url - The requests URL.
     * @param {RequestOptions} [requestOptions={}] - The requests options.
     * @returns {Promise} - Deferred ajax request.
     * @memberof RestHelper
     */
    static delete(url, requestOptions = {}) {
        requestOptions = {
            body: requestOptions.body || null,
            opt_headers: requestOptions.opt_headers || null,
            opt_params: requestOptions.opt_params || null,
            opt_timeout: requestOptions.opt_timeout || null,
            opt_sync: requestOptions.opt_sync || false,
            opt_withCredentials: requestOptions.opt_withCredentials || false,
            opt_callback: requestOptions.opt_callback || null
        };

        return RestHelper.request(url, "delete", requestOptions);
    }
    
    /**
     * Standard PATCH request.
     * 
     * @static
     * @param {string} url - The requests URL.
     * @param {RequestOptions} [requestOptions={}] - The requests options.
     * @returns {Promise} - Deferred ajax request.
     * @memberof RestHelper
     */
    static patch(url, requestOptions = {}) {
        requestOptions = {
            body: requestOptions.body || null,
            opt_headers: requestOptions.opt_headers || null,
            opt_params: requestOptions.opt_params || null,
            opt_timeout: requestOptions.opt_timeout || null,
            opt_sync: requestOptions.opt_sync || false,
            opt_withCredentials: requestOptions.opt_withCredentials || false,
            opt_callback: requestOptions.opt_callback || null
        };

        return RestHelper.request(url, "patch", requestOptions);
    }

    /**
     * Standard request.
     * 
     * @static
     * @param {string} url - The requests URL.
     * @param {"get"|"post"|"put"|"delete"|"patch"} method - The requests method.
     * @param {RequestOptions} [requestOptions={}] - The requests options.
     * @returns {Promise} - Deferred ajax request.
     * @memberof RestHelper
     */
    static request(url, method, requestOptions = {}) {
        return Ajax.request(
                url,
                method,
                requestOptions.body,
                requestOptions.opt_headers,
                requestOptions.opt_params,
                requestOptions.opt_timeout,
                requestOptions.opt_sync,
                requestOptions.opt_withCredentials
            ).then(
                xhrResponse => {
                    requestOptions.opt_callback && requestOptions.opt_callback(xhrResponse);
                    return xhrResponse;
                },
                error => {
                    console.error(error);
                    return error.toString();
                }
            );
    }
}

/**
* The requests options.
* 
* @typedef {Object} RequestOptions
* @property {string|null} [body=null] body
* @property {Object|null} [opt_headers=null] headers
* @property {Object|null} [opt_params=null] params
* @property {number|null} [opt_timeout=null] timeout
* @property {boolean} [opt_sync=false] sync
* @property {boolean} [opt_withCredentials=false] withCredentials
* @property {(xhrResponse: Object) => void} [opt_callback] callback
*/

const getRequest = RestHelper.get;
const postRequest = RestHelper.post;
const putRequest = RestHelper.put;
const deleteRequest = RestHelper.delete;
const patchRequest = RestHelper.patch;
const request = RestHelper.request;

export {
    RestHelper,
    getRequest,
    postRequest,
    putRequest,
    deleteRequest,
    patchRequest,
    request
};
export default RestHelper;