import React from 'react';
import UserService from '../../services/user.service';
import {User} from '../../models/user';
import './register.page.css';

export default class RegisterPage extends React.Component {

    constructor(props) {
        super(props);
        debugger;

        if (UserService.currentUserValue) {
            this.props.history.push('/');
        }

        this.state = {
            user: new User('', '', '', '',),
            submitted: false,
            loading: false,
            errorMessage: ''
        };
    }

    handleChange(e) {
        debugger;
        var {name, value} = e.target;
        var user = this.state.user;
        if (name === 'roles') {
            user[name].push(value);
        } else {
            user[name] = value;
        }
        this.setState({user: user});
    }

    async handleRegister(e) {
        e.preventDefault();
        this.setState({submitted: true});
        const {user} = this.state;

        if (!(user.username && user.password && user.email && user.roles)) {
            return;
        }

        this.setState({loading: true});
        debugger;
        UserService.register(user).then(data => {
            if (data.data.responseId === 2) {
                this.setState({errorMessage: data.data.message});
            } else {
                this.props.history.push("/login");
            }
        }, error => {

        });
    }

    render() {
        const {user, submitted, loading, errorMessage, roles} = this.state;
        return (
            <div className="col-md-12">
                <div className="card card-container">
                    <img id="profile-img" className="profile-img-card"
                         src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"/>
                    {errorMessage &&
                        <div className="alert alert-danger" role="alert">
                            <strong>Error! </strong> {errorMessage}
                        </div>
                    }
                    <form name="form" onSubmit={(e) => this.handleRegister(e)}>
                        <div className={'form-group' + (submitted && !user.username ? 'has-error' : '')}>
                            <label htmlFor="username">Username</label>
                            <input type="text" className="form-control" name="username" value={user.username}
                                   onChange={(e) => this.handleChange(e)}/>
                            {submitted && !user.username &&
                                <div className="help-block" style={{color: "red"}}>Username is required</div>
                            }
                        </div>
                        <div className={'form-group' + (submitted && !user.name ? 'has-error' : '')}>
                            <label htmlFor="email">Email</label>
                            <input type="text" className="form-control" name="email" value={user.name}
                                   onChange={(e) => this.handleChange(e)}/>
                            {submitted && !user.email &&
                                <div className="help-block" style={{color: "red"}}>Email is required</div>
                            }
                        </div>
                        <div className={'form-group' + (submitted && !user.password ? 'has-error' : '')}>
                            <label htmlFor="password">Password</label>
                            <input type="password" className="form-control" name="password" value={user.password}
                                   onChange={(e) => this.handleChange(e)}/>
                            {submitted && !user.password &&
                                <div className="help-block" style={{color: "red"}}>Password is required</div>
                            }
                        </div>
                        <div className="form-group">
                            <input type="checkbox" id="Admin" name="roles" value="Admin"
                                   onChange={(e) => this.handleChange(e)}/> Admin
                            <input type="checkbox" id="HR" name="roles" value="HR" style={{marginLeft: "1rem"}}
                                   onChange={(e) => this.handleChange(e)}/> HR
                            <input type="checkbox" id="EMPLOYEE" name="roles" value="EMPLOYEE"
                                   style={{marginLeft: "1rem"}} onChange={(e) => this.handleChange(e)}/> Employee
                            {submitted && user.roles.length === 0 &&
                                <div className="help-block" style={{color: "red"}}>Role is required</div>
                            }
                        </div>
                        <div className="form-group">
                            <button className="btn btn-lg btn-primary btn-block btn-signin form-submit-button"
                                    disabled={false}>Sign Up
                            </button>
                        </div>

                    </form>
                </div>
            </div>
        );
    }

}
