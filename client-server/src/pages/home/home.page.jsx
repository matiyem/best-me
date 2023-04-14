import React from 'react';
import UserService from '../../services/user.service';
import CourseService from '../../services/course.service';
import {User} from '../../models/user';
import {Transaction} from '../../models/transaction';
import userService from "../../services/user.service";
import {Toast} from "primereact/toast";
import {Button} from 'primereact/button';


export default class HomePage extends React.Component {

    constructor(props) {
        super(props);
        this.toast = React.createRef()
        this.state = {
            courses: [],
            currentUser: new User()
        };
    }
    showToast(severityValue, summaryValue, detailValue) {
        this.toast.current.show({severity: severityValue, summary: summaryValue, detail: detailValue})
    }

    componentDidMount() {
        UserService.currentUser.subscribe(data => {
            debugger;
            this.setState({
                currentUser: data
            });
        });
        if (this.state.currentUser != null) {
            this.getAllCourses();
        }
    }

    getAllCourses() {
        this.setState({
            courses: {loading: true}
        });
        debugger;
        CourseService.findAllCourses().then(courses => {
            debugger;
            this.setState({courses: courses.data});
        }, error => {
            if (error.response.status === 502) {
                userService.logOut();
                this.props.history.push("/login");
                userService.loadCaptcha();
            }
        });
    }

    enroll(course) {
        if (!this.state.currentUser) {
            this.showToast('Error', 'Error Message', 'To enroll a course, you should sign in.');
            this.props.history.push("/login");
        }
        var transaction = new Transaction(this.state.currentUser.id, course);
        CourseService.createTransaction(transaction).then(data => {
            if (data.data.statusId === 2) {
                this.showToast('success', 'Success Message', 'You enrolled the course successfully.');
            } else {
                this.showToast('warn', 'Warn Message', 'This course has already been selected.');
            }
        }, error => {
            debugger;
            if (error.response.status === 502) {
                userService.logOut();
                this.props.history.push("/login");
                userService.loadCaptcha();
            }
            this.showToast('Error', 'Error Message', 'Unexpected error occurred.');
        });
    }

    detail(course) {
        debugger;
        localStorage.setItem('currentCourse', JSON.stringify(course));
        this.props.history.push('/detail/' + course.id);
    }


    render() {
        debugger;
        const {courses} = this.state;
        return (
            <div className="col-md-12">
                <Toast ref={this.toast}/>

                {courses.loading && <em> Loading courses...</em>}
                {courses.length &&
                    <table className="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Course Title</th>
                            <th scope="col">Author</th>
                            <th scope="col">Detail</th>
                            <th scope="col">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {courses.map((course, index) =>
                            <tr key={course.id}>
                                <th scope="row">{index + 1}</th>
                                <td>{course.title}</td>
                                <td>{course.author}</td>
                                <td>
                                     <Button label="Detail" severity="info" size="sm" onClick={() => this.detail(course)}/>
                                </td>
                                <td>
                                    <Button label="Enroll" severity="danger" size="sm" onClick={() => this.enroll(course)}/>
                                </td>
                            </tr>
                        )}
                        </tbody>

                    </table>
                }

            </div>
        );
    }

}
