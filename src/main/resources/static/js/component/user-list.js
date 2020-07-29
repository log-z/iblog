import Pagination from '/static/js/component/pagination.js'

export default {
    components: {
        pagination: Pagination
    },
    props: [ 'supportOptions', 'defaultRange', 'listApi' ],
    data: function() {
        return {
            listApiUrl: (typeof this.listApi === 'undefined' ? '/api/user/list' : this.listApi),
            message: null,
            users: null,
            range: {
                num: null,
                offset: null,
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
                                <a v-if="viewable" v-bind:href="userHomeUrl(user.userId)">[查看]</a>
                                <a v-if="deletable" href="#" v-on:click.prevent="deleteUser(user.userId)">[删除]</a>
                            </td>
                        </tr>
                    </table>
                    <pagination v-bind:range="range" v-on:updated="rangeUpdate"></pagination>
                </div>
            `,
    methods: {
        userHomeUrl: function(userId) {
            return 'user.html#/' + userId;
        },
        userDeleteUrl: function(userId) {
            return '/api/user/' + userId
        },
        init: function() {
            this.range.num = this.defaultRange.num;
            this.range.offset = this.defaultRange.offset;
        },
        reload: function () {
            this.$axios.get(this.listApiUrl, {
                params: this.range,
            }).then(response => {
                if (response.status === 200) {
                    this.users = response.data.data.users;
                    this.range = response.data.data.range;
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
        rangeUpdate: function () {
            this.$emit('range-update')
        },
    },
}
