export default {
    props: [ 'user' ],
    template: `
            <ul>
                <li>ID：{{user.userId}}</li>
                <li>姓名：{{user.userName}}</li>
                <li v-if="user.userEmail">邮箱：{{user.userEmail}}</li>
            </ul>
        `,
}