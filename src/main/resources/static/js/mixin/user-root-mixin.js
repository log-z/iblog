import UserHeader from '/static/js/component/user-header.js'

export default {
    data: {
        baseApiUrl: '/api/user',
        currentUser: {
            userId: null,
            userName: null,
            userEmail: null,
        },
        targetUser: {
            userId: null,
            userName: null,
            userEmail: null,
        },
    },
    computed: {
        isMe: function () {
            let cid = this.currentUser.userId;
            let tid = this.targetUser.userId;
            return cid && tid && cid === tid;
        }
    },
    components: {
        'user-header': UserHeader,
    },
    methods: {
        reloadCurrentUserInfo: function () {
            this.$axios.get(this.baseApiUrl).then(response => {
                if (response.status === 200) {
                    this.currentUser = response.data.data.user;

                    // 当路由的用户参数空缺时，重定向到自己的页面
                    if (typeof this.$route.params.userId === 'undefined') {
                        this.$router.push(this.currentUser.userId)
                    }
                }
            }).catch(() => { });
        },
        reloadTargetUserInfo: function (userId) {
            let apiUrl = this.baseApiUrl + '/' + userId;

            this.clearTargetUserInfo();
            this.$axios.get(apiUrl).then(response => {
                if (response.status === 200) {
                    this.targetUser = response.data.data.user;
                }
            })
        },
        clearTargetUserInfo: function () {
            this.targetUser = {
                userId: null,
                userName: null,
                userEmail: null,
            }
        }
    },
    created: function () {
        this.reloadCurrentUserInfo();
    }
}