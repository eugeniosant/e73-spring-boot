import React, {Component} from 'react';

class App extends Component {
   constructor(props) {
      super(props);
      this.state = {
         buttonlabel: props.buttonlabel
      };
   }

   handleChange = e => {
      const input = e.target;
      this.setState({
         [input.name]: input.value,
      });
   };

   render() {
      const { buttonlabel } = this.state;
      return (
        <div>
           <h1>Simple MFE Configuration</h1>
           <div>
              <label htmlFor="buttonlabel">Button Label</label>
              <input id="buttonlabel" name="buttonlabel" defaultValue={buttonlabel} type="text" onChange={this.handleChange}  />
           </div>
        </div>
      );
   }
}

export default App;
