window.onload = () => {
    document.querySelectorAll('form').forEach((form) => {
        form.input_password = form.querySelectorAll('input[type=password]');
        form.onsubmit = function (event) {
            this.input_password.forEach((password) => {
                password.value = CryptoJS.SHA256(password.value).toString()
            })
        }
    })
}
