function range(x, y, z) {
    if (typeof z !== "number" || z === 0) z = 1;
    if (typeof y !== "number") return range(0, x, z)

    let len = Math.floor((y - x) / z)
    if (len >= 0) {
        x -= z;
        return Array.from({length:len}, () => x += z);
    } else {
        return [];
    }
}

function param_editor(url, name, value) {
    if (url.indexOf('?') === -1) {
        if (value !== null)
            return url + '?' + name + '=' + value;
    } else if (url.indexOf('?') === url.length - 1) {
        if (value != null)
            return url + name + '=' + value;
    } else {
        if (value === null) {
            return url.replace(new RegExp('((?<=\\?)||&)' + name + '=[^&]*(?=&||^)'), '');
        } else {
            let re = new RegExp('(?<=(\\?||&)' + name + '=)[^&]*(?=&||^)');
            if (url.search(re) !== -1) {
                return url.replace(re, value)
            } else if (url.indexOf('&') === url.length - 1) {
                return url + name + '=' + value;
            } else {
                return url + '&' + name + '=' + value;
            }
        }
    }
}
