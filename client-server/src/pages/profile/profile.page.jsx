import React from 'react';
import UserService from '../../services/user.service';
import CourseService from '../../services/course.service';
import userService from "../../services/user.service";

import {Toast} from 'primereact/toast';
import {Button} from 'primereact/button';

export default class ProfilePage extends React.Component {


    constructor(props) {
        super(props);
        this.toast = React.createRef()
        if (!UserService.currentUserValue) {
            this.props.history.push('/');
            return;
        }
        this.state = {
            user: UserService.currentUserValue,
            transactions: [],
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
                userService.loadCaptcha();
            }
        });
    }

    showToast(severityValue, summaryValue, detailValue) {
        this.toast.current.show({severity: severityValue, summary: summaryValue, detail: detailValue})
    }

    delete(transaction) {

        debugger;
        CourseService.deleteTransaction(transaction.id).then(data => {
            var finalTransaction = this.state.transactions.filter((tran) => {
                return tran.id !== transaction.id;
            });
            this.setState({
                transactions: finalTransaction
            })
            this.showToast('success', 'Success Message', 'The lesson was successfully deleted.');

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


    exportExcel() {
        const user = this.state.user;

        CourseService.exportExcel(user.id).then(response => {
            debugger;
            const outputFilename = `"profile"+ ${Date.now()}.xlsx`;

            // If you want to download file automatically using link attribute.
            const url = URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', outputFilename);
            document.body.appendChild(link);
            link.click();
        }, error => {
            debugger;
        })
    }

    exportPdf() {
        const user = this.state.user;

        CourseService.exportPdf(user.id).then(response => {
            debugger;
            const outputFilename = `"profile"+ ${Date.now()}.pdf`;
            const url = URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', outputFilename);
            document.body.appendChild(link);
            link.click();
        }, error => {
            debugger;
        })
    }

    render() {
        const {transactions} = this.state;
        return (
            <div className="col-md-12">

                <div className="jumbotron">
                    <h1 className="display-4">Hello, {this.state.user.name}</h1>
                </div>
                <Toast ref={this.toast}/>
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
                                    <Button
                                        label="Delete" severity="danger" size="sm"
                                        onClick={() => this.delete(transaction)}/>

                                </td>
                            </tr>
                        )
                        }
                        </tbody>
                    </table>
                }
                {transactions.length &&


                    <div className="flex flex-row-reverse flex-wrap card-container ">
                        <div
                            className="flex align-items-center justify-content-center w-8rem h-4rem  font-bold text-gray-900 border-round ">
                            <Button label="Export pdf"
                                    onClick={() => this.exportPdf()}/>
                        </div>
                        <div
                            className="flex align-items-center justify-content-center w-10rem h-4rem  font-bold text-gray-900 border-round ">
                            <Button label="Export excel"
                                    onClick={() => this.exportExcel()}/>

                        </div>

                    </div>
                }
            </div>
        );
    }

}
