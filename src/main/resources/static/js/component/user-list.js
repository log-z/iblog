import Pagination from '/static/js/component/pagination.js'

export default {
    components: {
        pagination: Pagination
    },
    props: {
        baseUrl: {
            type: String,
            default: function () {
                return '/api/user/list';
            },
        },
        supportOptions: {
            type: Array,
            default: function () {
                return ['view'];
            },
        },
        requestParam: {
            type: Object,
            default: function() {
                return {
                    userName: null,
                    userEmail: null,
                    fuzzySearch: null,
                }
            },
        },
        pageRange: {
            type: Object,
            default: function() {
                return {
                    pageNum: null,
                    pageSize: null,
                }
            },
        },
        refresh: {
            type: Number,
            default: null,
        },
    },
    data: function() {
        return {
            message: null,
            users: [],
            resPageRange: {
                pageNum: null,
                pages: null,
                pageSize: null,
                total: null,
            },
        }
    },
    computed: {
        viewable: function () {
            return this.supportOptions.includes('view');
        },
        deletable: function () {
            return this.supportOptions.includes('delete');
        },
    },
    template: `
                <div>
                    <p>{{message}}</p>
                    <table>
                        <tr>
                            <th>ID</th>
                            <th>名称</th>
                            <th>邮箱</th>
                            <th>操作</th>
                        </tr>
                        <tr v-for="user in users">
                            <td>{{user.userId}}</td>
                            <td>{{user.userName}}</td>
                            <td>{{user.userEmail}}</td>
                            <td>
                                <a v-if="viewable" :href="userHomeUrl(user.userId)">[查看]</a>
                                <a v-if="deletable" href="#" @click.prevent="deleteUser(user.userId)">[删除]</a>
                            </td>
                        </tr>
                    </table>
                    <pagination :page-range="resPageRange" @updated="rangeUpdate"></pagination>
                </div>
            `,
    methods: {
        userHomeUrl: function(userId) {
            return 'user.html#/' + userId;
        },
        userDeleteUrl: function(userId) {
            return '/api/user/' + userId
        },
        reload: function () {
            this.$axios.get(this.baseUrl, {
                params: {
                    ...this.requestParam,
                    'pageRange.pageNum': this.pageRange.pageNum,
                    'pageRange.pageSize': this.pageRange.pageSize,
                },
            }).then(response => {
                if (response.status === 200) {
                    this.users = response.data.data.users;
                    this.resPageRange = response.data.data.pageRange;
                }
            })
        },
        deleteUser: function (userId) {
            const apiUrl = this.userDeleteUrl(userId);
            this.$axios.delete(apiUrl).then(response => {
                if (response.status === 204) {
                    this.message = '删除用户成功，ID：' + userId;
                    this.reload();
                } else {
                    this.message = '删除用户失败，ID：' + userId;
                }
            }).catch(error => {
                this.message = error.response.data.errors.message;
            })
        },
        rangeUpdate: function (pageNum) {
            this.$emit('range-update', pageNum, this.pageRange.pageSize)
        },
    },
    watch: {
        refresh: function() {
            this.reload();
        },
    },
}
