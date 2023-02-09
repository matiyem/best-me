import axios from 'axios';
import {BehaviorSubject} from 'rxjs';

const API_URL = 'http://localhost:8080/auth/';


let currentUserSubject=new BehaviorSubject() ;
class UserService {
    get currentUserValue() {
        debugger;
        return new BehaviorSubject(JSON.parse(localStorage.getItem('currentUser'))).value;
    }

    get currentUser() {
        debugger;
        return new BehaviorSubject(JSON.parse(localStorage.getItem('currentUser'))).asObservable();
    }

    login(user) {
        debugger;
        return axios.post(
            API_URL + "login",
            JSON.stringify(user),
            {headers: {'Content-Type': 'application/json; charset=UTF-8'}}
        ).then(response => {
            if (response.data.token) {
                localStorage.setItem("currentUser", JSON.stringify(response.data));
                currentUserSubject= new BehaviorSubject(JSON.parse(localStorage.getItem('currentUser')));
            }
            return response.data;
        });
    }

    logOut() {
        debugger;
        localStorage.removeItem('currentUser');
        currentUserSubject.next(null);
        return;
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
