export default {
    props: ['range'],
    computed: {
        currentPage: function () {
            return Math.ceil(this.range.offset / this.range.num) + 1;
        },
        lastPage: function () {
            return Math.ceil(this.range.total / this.range.num);
        },
        hasPrevPage: function () {
            return this.range.offset - this.range.num >= 0;
        },
        hasNextPage: function () {
            return this.range.offset + this.range.num < this.range.total;
        },
    },
    template: `
                <div class="pagination">
                    第{{currentPage}}页，共{{lastPage}}页
                    <a href="#" v-if="hasPrevPage" v-on:click.prevent="loadPrevPage">[上一页]</a>
                    <a href="#" v-if="hasNextPage" v-on:click.prevent="loadNextPage">[下一页]</a>
                </div>
            `,
    methods: {
        loadPrevPage: function() {
            this.range.offset -= this.range.num;
            this.$emit('updated');
        },
        loadNextPage: function() {
            this.range.offset += this.range.num;
            this.$emit('updated');
        },
    }
}
