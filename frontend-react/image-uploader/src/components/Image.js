import React from 'react';
import { Component } from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import { Container, Button, Card, Row, Col, Image } from 'react-bootstrap';
import axios from 'axios';


export class ImageUploader extends Component{
    constructor(props){
        super(props)
        this.state = {
            selectedFiles: []
        }
    }
    
    onFileSelect = (event) => {
        this.setState({
            selectedFiles: event.target.files
        }, 
        () => {
            console.log(this.state.selectedFiles)
        }
        )
    }

    onFileUpload = () => {
        if(this.state.selectedFiles.length > 0){
            let fd = new FormData()
            for (const key of Object.keys(this.state.selectedFiles)) {
                fd.append('images', this.state.selectedFiles[key])
            }
            axios.post("http://localhost:8081/api/v1/image/upload", fd,
            {
                headers: {
                    'Content-Type': 'multipart/form-data',
                }
            }, 
            {
            }).then(response => {
                console.log((response.data))
            }).catch(error => {
                console.log(error)
            })
        }
    }

    render(){
        return (
            <div id="imageUploader">
                <Card className="m-3">
                    <Card.Body>
                        <Card.Title>Image Uploader</Card.Title>
                        <Card.Text>Upload Your Moments</Card.Text>
                        <input type="file" multiple accept=".jpg, .jpeg, .png" onChange={this.onFileSelect}></input>
                        <br></br>
                        <Button variant="outline-primary" className="m-1" onClick={this.onFileUpload}>Upload</Button>
                    </Card.Body>
                </Card>
            </div>
        );
    }
}

export class ImageList extends Component{

    constructor(props){
        super(props)
        this.state = {
            images: []
        }
    }

    componentDidMount(){
        axios.get('http://localhost:8081/api/v1/image/all')
           .then(response => {
                console.log(response)
                this.setState({
                    images: response.data
                })
            }) 
    }

    getImageContainer(){
        let items = this.state.images.map((item, index) => {
            return (
                <Col xs={6} md={4} key={index}>
                    <Image className="m-2" src={item} rounded />
                </Col>
            );
        });
        return items;
    }

    render(){
        return (
            <div id="imageList">
            <Card className="m-3">
                <Card.Body>
                    <Card.Title>List of Images</Card.Title>
                    <Card.Text>Your Moments</Card.Text>
                    <Container>
                        <Row>
                            {this.getImageContainer()}
                        </Row>
                    </Container>
                </Card.Body>
            </Card>
        </div>
        );
    }
}