window.addLoadEvent = function (callback) {
    if (typeof window.loadEvent === 'undefined') {
        window.loadEvent = [callback];
        window.onload = ev => {
            window.loadEvent.forEach(callback => callback(ev))
        }
    } else {
        window.loadEvent.push(callback);
    }
}
