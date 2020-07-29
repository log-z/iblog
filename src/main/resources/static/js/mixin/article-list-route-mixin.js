import MultipageRouteMixin from '/static/js/mixin/multipage-route-mixin.js'

const mixin = MultipageRouteMixin;

mixin.methods.routeUpdate = function (to, form) {
    let num = Number(to.query.num);
    let offset = Number(to.query.offset);

    this.page.keyword = to.query.keyword;
    if (Number.isNaN(num) || Number.isNaN(offset)) {
        this.page.init();
    } else {
        this.page.range.num = num;
        this.page.range.offset = offset;
    }

    this.page.reload();
}

export default mixin;
