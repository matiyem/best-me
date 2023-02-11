import axios from 'axios';
import {BehaviorSubject} from 'rxjs';

const API_URL = 'http://localhost:8080/auth/';

const currentUserSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('currentUser')));

class UserService {
    login(user) {
        debugger;
        return axios.post(
            API_URL + "login",
            JSON.stringify(user),
            {headers: {'Content-Type': 'application/json; charset=UTF-8'}}
        ).then(response => {
            if (response.data.token) {
                localStorage.setItem("currentUser", JSON.stringify(response.data));
                currentUserSubject.next(response.data);

            }
            return response.data;
        });
    }

    logOut() {
        localStorage.removeItem('currentUser');
        currentUserSubject.next(null);

    }

    register(user) {
        debugger;
        return axios.post(
            API_URL + 'register',
            JSON.stringify(user),
            {headers: {'Content-Type': 'application/json; charset=UTF-8'}});
    }
    get currentUser(){
        return currentUserSubject.asObservable();

    }
    get currentUserValue(){
        return currentUserSubject.value;
    }
}

export default new UserService();
