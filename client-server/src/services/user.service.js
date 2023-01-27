import axios from 'axios';
import {BehaviorSubject} from 'rxjs';

const API_URL = 'http://localhost:8080/user/';

const currentUserSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('currentUser')));

class UserService {
    get currentUserValue() {
        return currentUserSubject.value;
    }

    get currentUser() {
        return currentUserSubject.asObservable();
    }

    login(user) {
        debugger;
        const headers = {
            authorization:'Basic ' + btoa(user.username + ':' + user.password)
        };
        return axios.post(API_URL + "loginUser", headers,{

        "username": "ds2525",
        "password": "donotforgetme",
        "email": "ds2525@gmail.com",
        "roles": ["Admin","Manager"]

        }).then(response => {
            if (response.data.accessToken) {
                localStorage.setItem("user", JSON.stringify(response.data));
            }

            return response.data;
        });
        // const headers = {
        //   authorization:'Basic ' + btoa(user.username + ':' + user.password)
        // };
        // return axios.get(API_URL + 'login', {headers: headers}).then(response => {
        //   localStorage.setItem('currentUser', JSON.stringify(response.data));
        //   currentUserSubject.next(response.data);
        // });
    }

    logOut() {
        return axios.post(API_URL + 'logout', {}).then(response => {
            localStorage.removeItem('currentUser');
            currentUserSubject.next(null);
        })
    }

    register(user) {
        debugger;
        return axios.post(API_URL + 'saveUser', JSON.stringify(user),
            {headers: {'Content-Type': 'application/json; charset=UTF-8'}});
    }

}

export default new UserService();
