export default {
    template: `
                <ul>
                    <li><a href="admin.html">管理员主页</a></li>
                    <li><a href="admin-manage-users.html">管理用户</a></li>
                    <li><a href="admin-manage-articles.html">管理文章</a></li>
                    <li><a href="#" v-on:click.prevent="logout">退出后台</a></li>
                </ul>
            `,
    methods: {
        logout: function () {
            const apiUrl = '/api/admin/session';
            const jumpUrl = 'admin-login.html';
            this.$axios.delete(apiUrl).then(response => {
                if (response.status === 204) {
                    window.location = jumpUrl;
                }
            })
        }
    },
}