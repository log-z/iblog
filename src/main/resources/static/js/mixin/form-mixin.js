export default {
    methods: {
        clearMessages: function () {
            this.message = null;
            for (let f in this.errors) {
                if (this.errors.hasOwnProperty(f))
                    this.errors[f] = null;
            }
        },
        formDefaultHandle: function (response) {
            this.message = response.data.data.message;
        },
        formErrorHandle: function (error) {
            this.message = error.response.data.errors.message;
            if (error.response.status === 400) {
                error.response.data.errors.details.forEach(de => {
                    if (typeof this.errors[de.target] !== 'undefined') {
                        if (this.errors[de.target] === null) {
                            this.errors[de.target] = de.message;
                        } else {
                            this.errors[de.target] += '; ' + de.message;
                        }
                    }
                })
            }
        }
    }
}
