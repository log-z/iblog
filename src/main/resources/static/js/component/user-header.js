export default {
    props: ['user'],
    template: `
                <ul v-if="user.userId">
                    <li><a href="index.html">iBlog</a></li>
                    <li><a href="article-editor.html">写文章</a></li>
                    <li><a v-bind:href="myHomeUrl">我的主页</a></li>
                    <li><a v-bind:href="myInfoUrl">我的信息</a></li>
                    <li><a href="#" v-on:click.prevent="logout">退出账号</a></li>
                </ul>
                <ul v-else>
                    <li><a href="index.html">iBlog</a></li>
                    <li><a href="user-login.html">登陆</a></li>
                    <li><a href="user-register.html">注册</a></li>
                </ul>
            `,
    computed: {
        myHomeUrl: function () {
            return 'user.html#/' + this.user.userId;
        },
        myInfoUrl: function () {
            return 'user-info.html#/' + this.user.userId;
        }
    },
    methods: {
        logout: function () {
            const apiUrl = '/api/user/session';
            const jumpUrl = 'user-login.html';
            this.$axios.delete(apiUrl).then(response => {
                if (response.status === 204) {
                    window.location = jumpUrl;
                }
            })
        }
    },
}