import Ajax from 'metal-ajax';

class StagingRest {
    static get(url, body = null, opt_headers = null, opt_params = null, opt_timeout = null, opt_sync = false, opt_withCredentials = false, opt_callback = null) {
        return StagingRest.request(url, "get", body, opt_headers, opt_params, opt_timeout, opt_sync, opt_withCredentials);
    }
    
    static post(url, body = null, opt_headers = null, opt_params = null, opt_timeout = null, opt_sync = false, opt_withCredentials = false, opt_callback = null) {
        return StagingRest.request(url, "post", body, opt_headers, opt_params, opt_timeout, opt_sync, opt_withCredentials);
    }
    
    static put(url, body = null, opt_headers = null, opt_params = null, opt_timeout = null, opt_sync = false, opt_withCredentials = false, opt_callback = null) {
        return StagingRest.request(url, "put", body, opt_headers, opt_params, opt_timeout, opt_sync, opt_withCredentials);
    }
    
    static delete(url, body = null, opt_headers = null, opt_params = null, opt_timeout = null, opt_sync = false, opt_withCredentials = false, opt_callback = null) {
        return StagingRest.request(url, "delete", body, opt_headers, opt_params, opt_timeout, opt_sync, opt_withCredentials);
    }
    
    static patch(url, body = null, opt_headers = null, opt_params = null, opt_timeout = null, opt_sync = false, opt_withCredentials = false, opt_callback = null) {
        return StagingRest.request(url, "patch", body, opt_headers, opt_params, opt_timeout, opt_sync, opt_withCredentials);
    }

    static request(url, method, body = null, opt_headers = null, opt_params = null, opt_timeout = null, opt_sync = false, opt_withCredentials = false, opt_callback = null) {
        return Ajax.request(url, method, body, opt_headers, opt_params, opt_timeout, opt_sync, opt_withCredentials)
            .then(xhrResponse => {
                return xhrResponse;
            }, error => {
                console.error(error);
                return error.toString();
            });
    }
}

const StagingRestGet = StagingRest.get;
const StagingRestPost = StagingRest.post;
const StagingRestPut = StagingRest.put;
const StagingRestDelete = StagingRest.delete;
const StagingRestPatch = StagingRest.patch;

export {
    StagingRest,
    StagingRestGet,
    StagingRestPost,
    StagingRestPut,
    StagingRestDelete,
    StagingRestPatch
};
export default StagingRest;