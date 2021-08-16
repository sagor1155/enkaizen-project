import './App.css';
import { Component } from 'react';
import {ImageUploader, ImageList} from './components/Image';
import SockJsClient from 'react-stomp';

const SOCKET_URL = 'http://localhost:8081/ws-message';

class App extends Component {
    constructor(props){
        super(props)
        this.state = {
            message: "",
            reload: false
        }
    }
    
    onConnected() {
        console.log("Connected!!")
    }
    
    onDisconnected() {
        console.log("Disconnected!!")
    }

    onMessageReceived(msg) {
        console.log("message received: " + msg.message)
        this.setState({
            message: msg.message
        })
        window.location.reload();
    }
    
    render (){
        return (
            <div className="App">
                <SockJsClient
                    url={SOCKET_URL}
                    topics={['/topic/message']}
                    onConnect={this.onConnected}
                    onDisconnect={this.onDisconnected}
                    onMessage={msg => this.onMessageReceived(msg)}
                    debug={false}
                />
                <h1>Image Uploader Application</h1>
                <ImageUploader></ImageUploader>
                <ImageList></ImageList>
            </div>
        );
    }
}

export default App;
