import MultipageRouteMixin from '/static/js/mixin/multipage-route-mixin.js'

const mixin = MultipageRouteMixin;

mixin.methods.routeUpdate = function (to, form) {
    let userId = to.params.userId;
    let num = Number(to.query.num);
    let offset = Number(to.query.offset);

    this.page.feature.authorId = userId;
    if (Number.isNaN(num) || Number.isNaN(offset)) {
        this.page.init();
    } else {
        this.page.range.num = num;
        this.page.range.offset = offset;
    }

    this.page.reload();
    this.$emit('author-update', userId)
}

export default mixin;
