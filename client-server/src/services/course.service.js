import axios from 'axios';

let API_URL = 'http://localhost:8080/clientService1/service/';

class CourseService {
    createTransaction(transaction) {
        return axios.post(API_URL + 'enroll', JSON.stringify(transaction),
            {
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                    "Authorization": JSON.parse(localStorage.getItem("currentUser")) != null ? JSON.parse(localStorage.getItem("currentUser")).token : ""
                }
            });
    }

    filterTransactions(userId) {
        return axios.get(API_URL + 'user/' + userId,
            {
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                    "Authorization": JSON.parse(localStorage.getItem("currentUser")) != null ? JSON.parse(localStorage.getItem("currentUser")).token : ""
                }
            });
    }

    filterStudents(courseId) {
        return axios.get(API_URL + 'course/' + courseId,
            {
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                    "Authorization": JSON.parse(localStorage.getItem("currentUser")) != null ? JSON.parse(localStorage.getItem("currentUser")).token : ""
                }
            });
    }

    findAllCourses() {
        debugger;
        return axios.get(
            API_URL + 'all',
            {
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                    "Authorization": JSON.parse(localStorage.getItem("currentUser")) != null ? JSON.parse(localStorage.getItem("currentUser")).token : ""
                }
            })
    }

    deleteTransaction(transactionId) {
        return axios.delete(API_URL + 'deleteCourse/' + transactionId,
            {
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                    "Authorization": JSON.parse(localStorage.getItem("currentUser")) != null ? JSON.parse(localStorage.getItem("currentUser")).token : ""
                }
            });
    }

}

export default new CourseService();
