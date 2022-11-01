import axios from 'axios';
export function policy() {
    return new Promise((resolve, reject) => {
        axios
            .post(
                "/demo/oSS/policy",
                {},
                {
                    headers: {
                        token: sessionStorage.getItem("token"),
                    },
                }
            )
            .then((res) => {
                console.log("正确===============111")
                resolve(res);
            })
         
    })
}


