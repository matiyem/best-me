import React from 'react';
import UserService from '../../services/user.service';
import CourseService from '../../services/course.service';
import {User} from '../../models/user';
import {Transaction} from '../../models/transaction';
import userService from "../../services/user.service";

export default class HomePage extends React.Component {

    constructor(props) {
        super(props);
        debugger
        this.state = {
            courses: [],
            errorMessage: '',
            infoMessage: '',
            warningMessage: '',
            currentUser: new User()
        };
    }

    componentDidMount() {
        UserService.currentUser.subscribe(data => {
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

        CourseService.findAllCourses().then(courses => {
            debugger;
            this.setState({courses: courses.data});
        }, error => {
            if (error.response.status === 502) {
                userService.logOut();
                this.props.history.push("/login");
            }
        });
    }

    enroll(course) {
        if (!this.state.currentUser) {
            this.setState({errorMessage: 'To enroll a course, you should sign in.'});
            this.props.history.push("/login");
        }
        var transaction = new Transaction(this.state.currentUser.id, course);
        CourseService.createTransaction(transaction).then(data => {
            if (data.data.statusId === 2) {
                this.setState({infoMessage: 'You enrolled the course successfully.'});
                setTimeout(() => {
                    document.getElementById("SuccessfullId").hidden = true;
                    this.setState({
                        infoMessage: ''
                    })
                }, 3000);


            } else {
                this.setState({warningMessage: 'This course has already been selected.'});
                setTimeout(() => {
                    document.getElementById("warningId").hidden = true;
                    this.setState({
                        warningMessage: ''
                    })
                }, 3000);
            }
        }, error => {
            debugger;
            if (error.response.status===502){
                userService.logOut();
                this.props.history.push("/login");
            }
            this.setState({errorMessage: 'Unexpected error occurred.'});
            setTimeout(() => {
                document.getElementById("error").hidden = true;
                this.setState({
                    errorMessage: ''
                })
            }, 3000);
        });
    }

    detail(course) {
        debugger;
        localStorage.setItem('currentCourse', JSON.stringify(course));
        this.props.history.push('/detail/' + course.id);
    }


    render() {
        const {courses, infoMessage, errorMessage, warningMessage} = this.state;
        return (
            <div className="col-md-12">
                {infoMessage &&
                    <div className="alert alert-success" id="SuccessfullId">
                        <strong>Successfull! </strong>{infoMessage}
                        <button type="button" className="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                }
                {errorMessage &&
                    <div className="alert alert-danger" id="error">
                        <strong>Error! </strong>{errorMessage}
                        <button type="button" className="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                }{warningMessage &&
                <div className="alert alert-warning" id="warningId">
                    <strong>Warning! </strong>{warningMessage}
                    <button type="button" className="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            }
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
                                    <button className="btn btn-info" onClick={() => this.detail(course)}>Detail</button>
                                </td>
                                <td>
                                    <button className="btn btn-success" onClick={() => this.enroll(course)}>Enroll
                                    </button>
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
