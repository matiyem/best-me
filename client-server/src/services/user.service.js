import axios from 'axios';
import {BehaviorSubject} from 'rxjs';

const API_URL = 'http://localhost:8080/auth/';

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
        return axios.post(
            API_URL + "login",
            JSON.stringify(user),
            {headers: {'Content-Type': 'application/json; charset=UTF-8'}}
        ).then(response => {
            if (response.data.token) {
                localStorage.setItem("user", JSON.stringify(response.data));

            }

            return response.data;
        });
    }

    logOut() {
        return axios.post(API_URL + 'logout', {}).then(response => {
            localStorage.removeItem('currentUser');
            currentUserSubject.next(null);
        })
    }

    register(user) {
        debugger;
        return axios.post(
            API_URL + 'register',
            JSON.stringify(user),
            {headers: {'Content-Type': 'application/json; charset=UTF-8'}});
    }

}

export default new UserService();
