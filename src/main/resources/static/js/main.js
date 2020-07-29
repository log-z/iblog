Vue.prototype.$axios = axios;
Vue.prototype.$qs = Qs;
Vue.prototype.$qs = Qs;

Vue.prototype.$axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
Vue.prototype.$axios.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
Vue.prototype.$axios.defaults.headers.delete['Content-Type'] = 'application/x-www-form-urlencoded';
