export default {
    data: function () {
        return {
            defaultPageRange: {
                pageNum: 1,
                pageSize: 3,
            },
            pageRange: {
                pageNum: 1,
                pageSize: 3,
            },
            refreshTime: null,
        }
    },
    methods: {
        pageRangeUpdate: function (pageNum, pageSize) {
            this.$router.push({
                path: this.$route.path,
                query: Object.assign({}, this.$route.query, {
                    pageNum: pageNum,
                    pageSize: pageSize,
                })
            })
        },
        routeUpdate: function (to, form) {
            if (Object.keys(to.query).length === 0) {
                this.pageRange.pageNum = this.defaultPageRange.pageNum;
                this.pageRange.pageSize = this.defaultPageRange.pageSize;
                this.requestParam = {};

                this.refreshTime = Date.now();
                return;
            }

            let clear = false;
            for (let pn in to.query) {
                if (!to.query.hasOwnProperty(pn))
                    continue;

                let pv = to.query[pn];
                if (pn === 'pageNum') {
                    let pageNum = Number(pv);
                    this.pageRange.pageNum = (pv === null || Number.isNaN(pageNum)) ? null : pageNum;
                } else if (pn === 'pageSize') {
                    let pageSize = Number(pv);
                    this.pageRange.pageSize = (pv === null || Number.isNaN(pageSize)) ? null : pageSize;
                } else {
                    if (!clear) {
                        this.requestParam = {}
                        clear = true;
                    }

                    this.requestParam[pn] = pv;
                }
            }
            this.refreshTime = Date.now();
        }
    },
    beforeRouteEnter: function (to, form, next) {
        next(vm => vm.routeUpdate(to, form))
    },
    beforeRouteUpdate: function(to, form, next) {
        next(this.routeUpdate(to, form));
    },
}
