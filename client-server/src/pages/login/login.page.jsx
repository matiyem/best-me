import React from 'react';
import UserService from '../../services/user.service';
import {User} from '../../models/user';
import './login.page.css';

export default class LoginPage extends React.Component {

    constructor(props) {
        super(props);
        debugger;

        if (UserService.currentUserValue) {
            this.props.history.push('/');
        }

        this.state = {
            user: new User('', '','','','',''),
            submitted: false,
            loading: false,
            errorMessage: '',
        };
    }

    componentDidMount() {
        debugger;
        const user = this.state.user;
        UserService.loadCaptcha(user).then(data => {
            debugger;
            this.setState({
                user: data.data,
            })
        }, error => {
            debugger;

        });
    }

    handleChange(e) {
        var {name, value} = e.target;
        var user = this.state.user;
        user[name] = value;
        this.setState({user: user});
    }

    handleLogin(e) {
        debugger;
        e.preventDefault();

        this.setState({submitted: true});
        const {user} = this.state;

        if (!(user.username && user.password)) {
            return;
        }

        this.setState({loading: true});
        UserService.login(user).then(data => {
            if (data.token === null) {
                this.setState({
                    errorMessage: "Username or password is not valid",
                    loading: false
                });
            } else {
                this.setState({
                    user: data
                });
                debugger;
                this.props.history.push("/home");
            }
        }, error => {
            this.setState({
                errorMessage: error,
                loading: false
            });
        });
    }

    changeCaptcha(e) {
        debugger;
        const {user} = this.state;
        UserService.loadCaptcha(user).then(data => {
            debugger;
            this.setState.user({
                captcha: data.data.realCaptcha,
                hiddenCaptcha:data.data.hiddenCaptcha

            })
        }, error => {
            debugger;

        });
    }

    render() {
        const {user, submitted, loading, errorMessage} = this.state;
        debugger;
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
                    <form name="form" onSubmit={(e) => this.handleLogin(e)}>
                        <div className={'form-group' + (submitted && !user.username ? 'has-error' : '')}>
                            <label htmlFor="username">Username</label>
                            <input type="text" className="form-control" name="username" value={user.username}
                                   onChange={(e) => this.handleChange(e)}/>
                            {submitted && !user.username &&
                                <div className="help-block">Username is required</div>
                            }
                        </div>
                        <div className={'form-group' + (submitted && !user.password ? 'has-error' : '')}>
                            <label htmlFor="password">Password</label>
                            <input type="password" className="form-control" name="password" value={user.password}
                                   onChange={(e) => this.handleChange(e)}/>
                            {submitted && !user.password &&
                                <div className="help-block">Password is required</div>
                            }
                        </div>
                        <div className={'form-group' + (submitted && !user.captcha ? 'has-error' : '')}>
                            <label htmlFor="captcha">Captcha</label>
                            <input type="text" className="form-control" name="captcha" value={user.captcha}
                                   onChange={(e) => this.handleChange(e)}/>
                            {submitted && !user.captcha &&
                                <div className="help-block">captcha is required</div>
                            }
                        </div>
                        <div className="form-group">
                            <button className="btn btn-lg btn-primary btn-block btn-signin form-submit-button"
                                    disabled={loading}>Login
                            </button>
                        </div>
                        <div className="form-group">
                            <div className="col-4" style={{display: "flex"}}>

                                <div style={{flex: 1}}><img src={`data:image/jpeg;base64,${user.realCaptcha}`}/></div>
                                {/*<div  style={{flex:1}}  onClick={(e) => this.handleChange(e)}><span className="glyphicon glyphicon-refresh"></span></div>*/}
                               <div onClick={(e) => this.changeCaptcha(e)}> <i className="bi bi-arrow-counterclockwise" ></i></div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        );
    }

}
