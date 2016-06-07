var Nav = React.createClass({
  render: function() {
    return (
      <nav classNameName="navbar navbar-inverse navbar-fixed-top">
        <div className="container">
          <div className="navbar-header">
            <button type="button" className="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
              <span className="sr-only">Toggle navigation</span>
              <span className="icon-bar"></span>
              <span className="icon-bar"></span>
              <span className="icon-bar"></span>
            </button>
            <a className="navbar-brand" href="#">Project name</a>
          </div>
          <div id="navbar" className="collapse navbar-collapse">
            <ul className="nav navbar-nav">
              <li className="active"><a href="#">Home</a></li>
              <li><a href="#about">SignUp</a></li>
              <li><a href="#contact">SignIn</a></li>
            </ul>
          </div>{/*.nav-collapse */}
        </div>
      </nav>
    )
  }
});

var Main = React.createClass({
  render: function() {
    return (
      <div>{/* 최상위 엘리먼트는 하나!! class 대신 className 을 */}
        {/* 네비 컴포넌트 */}
        <Nav/>

        <div className="container">

          <div className="starter-template">
            <h1>Bootstrap starter template</h1>
            <p className="lead">Use this document as a way to quickly start any new project.<br/>
              All you get is this text and a mostly barebones HTML document.</p>
          </div>

        </div>{/*.nav-collapse */}

      </div>
    )
  }
});

var SignUp = React.createClass({
  render: function() {
    return (
      <div className="container">

        <div className="starter-template">
          <h1>Sign Up</h1>
        </div>

      </div>
    )
  }
});

var SignIn = React.createClass({
  render: function() {
    return (
      <div className="container">

        <div className="starter-template">
          <h1>Sign In</h1>
        </div>

      </div>
    )
  }
});

var App = React.createClass({
  getInitialState: function() {
    return {
      route: window.location.hash.substr(1)
    }
  },

  componentDidMount: function() {
    window.addEventListener('hashchange', () => {
      this.setState({
        route: window.location.hash.substr(1)
      });
    })
  },

  render: function() {
    var Child;
    switch (this.state.route) {
      case 'main':      // localhost:8080/#main
        Child = Main;
        break;
      case 'singup':    // localhost:8080/#signup
        Child = SignUp;
        break;
      default:
        Child = Main;
    }
    return (<Child/>);
  }


});

// body 에 들어감
React.render(<App/>, document.body);




///* 최상위 엘리먼트는 하나!! class 대신 className 을 */
// 모듈화 해야 됨(분리 해야 함)
// 분리하면 다시 합쳐주는 webpack 이나 browserify 가 필요함.
