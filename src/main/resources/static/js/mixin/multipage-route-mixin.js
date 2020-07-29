export default {
    methods: {
        rangeUpdate: function () {
            this.$router.push({
                path: this.$route.path,
                query: Object.assign({}, this.$route.query, {
                    num: this.page.range.num,
                    offset: this.page.range.offset,
                })
            })
        },
        routeUpdate: function (to, form) {
            let num = Number(to.query.num);
            let offset = Number(to.query.offset);
            if (Number.isNaN(num) || Number.isNaN(offset)) {
                this.page.init();
            } else {
                this.page.range.num = num;
                this.page.range.offset = offset;
            }
            this.page.reload();
        }
    },
    beforeRouteEnter: function (to, form, next) {
        next(vm => vm.routeUpdate(to, form))
    },
    beforeRouteUpdate: function(to, form, next) {
        next(this.routeUpdate(to, form));
    }
}
