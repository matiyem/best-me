import React from 'react';
import UserService from '../../services/user.service';
import CourseService from '../../services/course.service';
import {Transaction} from "../../models/transaction";
import userService from "../../services/user.service";

export default class ProfilePage extends React.Component {

    constructor(props) {
        super(props);
        if (!UserService.currentUserValue) {
            this.props.history.push('/');
            return;
        }
        this.state = {
            user: UserService.currentUserValue,
            transactions: []
        };
    }

    componentDidMount() {
        debugger;
        this.setState({
            transactions: {loading: true}
        });
        const user = this.state.user;
        CourseService.filterTransactions(user.id).then(transactions => {
            this.setState({transactions: transactions.data});
        }, error => {
            if (error.response.status === 502) {
                userService.logOut();
                this.props.history.push("/login");
            }
        });
    }

    delete(transaction) {
        debugger;
        CourseService.deleteTransaction(transaction.id).then(data => {

            this.setState({
                infoMessage: 'The lesson was successfully deleted '
            })

            var finalTransaction=this.state.transactions.filter((tran)=>{
                return tran.id !==transaction.id;
            });
            this.setState({
                transactions:finalTransaction
            })
            setTimeout(() => {
                document.getElementById("SuccessfullId").hidden = true;
                this.setState({
                    errorMessage: ''
                })
            }, 3000);

        }, error => {
            debugger;
            if (error.response.status === 502) {
                userService.logOut();
                this.props.history.push("/login");
            }
            this.setState({
                errorMessage: "Unexpected error occurred.'"
            })
            setTimeout(() => {
                document.getElementById("error").hidden = true;
                this.setState({
                    errorMessage: ''
                })
            }, 3000);
        });
    }

    render() {
        const {transactions, infoMessage, errorMessage} = this.state;
        return (
            <div className="col-md-12">

                <div className="jumbotron">
                    <h1 className="display-4">Hello, {this.state.user.name}</h1>
                </div>
                {infoMessage &&
                    <div className="alert alert-success" id="SuccessfullId">
                        <strong>Successfull! </strong>{infoMessage}
                        <button type="button" className="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                }{errorMessage &&
                <div className="alert alert-danger" id="error">
                    <strong>Error! </strong>{errorMessage}
                    <button type="button" className="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            }
                {transactions.loading && <em>Loading transactions...</em>}
                {transactions.length &&
                    <table className="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Course Title</th>
                            <th scope="col">Author</th>
                            <th scope="col">Category</th>
                            <th scope="col">Enroll Date</th>
                            <th scope="col">Delete</th>
                        </tr>
                        </thead>
                        <tbody>
                        {transactions.map((transaction, index) =>
                            <tr key={transaction.id}>
                                <th scope="row">{index + 1}</th>
                                <td>{transaction.course.title}</td>
                                <td>{transaction.course.author}</td>
                                <td>{transaction.course.category}</td>
                                <td>{transaction.dateOfIssue}</td>
                                <td>
                                    <button className="btn btn-danger" onClick={() => this.delete(transaction)}>Delete
                                    </button>
                                </td>
                            </tr>
                        )
                        }
                        </tbody>
                    </table>
                }
            </div>
        );
    }

}
