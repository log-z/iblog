function init_pagination() {
    document.querySelectorAll('.pagination').forEach((pagination) => {
        let size = pagination.getAttribute('page-size');
        let offset = pagination.getAttribute('page-offset');
        let count = pagination.getAttribute('total');
        if (size < 0 || offset < 0 || count < 0) return;

        let index = Math.ceil(offset / size);
        let index_end = Math.ceil(count / size) - 1;
        let indexes;
        if (index <= 3) {
            indexes = range(0, index + 1);
        } else {
            indexes = [0, '...', index - 1, index];
        }
        if (index_end - index < 4) {
            indexes = indexes.concat(range(index + 1, index_end + 1));
        } else {
            indexes = indexes.concat(range(index + 1, index + 3));
            indexes.push('...', index_end);
        }

        let url = param_editor(window.location.href, 'num', size);
        indexes.forEach((i) => {
            let element;
            if (i === '...') {
                element = document.createElement('span');
                element.innerText = i;
            } else {
                element = document.createElement('a');
                element.innerText = i + 1;
                element.setAttribute('href', param_editor(url, 'offset', i * size));
            }
            element.setAttribute('class', 'page')
            pagination.appendChild(element);
        })
    })
}

window.addLoadEvent(() => {
    init_pagination();
})
