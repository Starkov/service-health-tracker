import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class ServiceList extends Component {

    constructor(props) {
        super(props);
        this.state = {services: [], isLoading: true};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch('api/v1/services')
            .then(response => response.json())
            .then(data => this.setState({services: data, isLoading: false}));
    }

    async remove(id) {
        await fetch(`/api/v1/services/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedServices = [...this.state.services].filter(it => it.id !== id);
            this.setState({services: updatedServices});
        });
    }

    render() {
        const {services, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const serviceList = services.map(service => {

            return <tr key={service.id}>
                <td style={{whiteSpace: 'nowrap'}}>{service.description}</td>
                <td>{service.url}</td>
                <td>{service.status}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/services/" + service.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(service.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/services/new">Add Service</Button>
                    </div>
                    <h3>Services: </h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">Description</th>
                            <th width="20%">URL</th>
                            <th>Status</th>
                            <th width="10%">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {serviceList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default ServiceList;