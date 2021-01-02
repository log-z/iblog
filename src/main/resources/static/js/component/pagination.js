export default {
    props: {
        pageRange: {
            type: Object,
            default: function() {
                return {
                    pageNum: 0,
                    pages: 0,
                    pageSize: 0,
                    total: 0,
                }
            },
        },
    },
    template: `
        <div class="pagination">
            总共{{pageRange.total}}条，共{{pageRange.pages}}页，第{{pageRange.pageNum}}页
            <a href="#" v-if="pageRange.pageNum > 1" @click.prevent="loadPrevPage">[上一页]</a>
            <a href="#" v-if="pageRange.pageNum < pageRange.pages" @click.prevent="loadNextPage">[下一页]</a>
        </div>
    `,
    methods: {
        loadPrevPage: function() {
            this.$emit('updated', this.pageRange.pageNum - 1);
        },
        loadNextPage: function() {
            this.$emit('updated', this.pageRange.pageNum + 1);
        },
    }
}
