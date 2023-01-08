import njord from './njord.png'
import './App.css';
import {Routes, Route, Outlet} from "react-router-dom";
import React, {useState} from "react";
import NavBar from "./NavBar";
import Enc from "./Enc";
import ControlPanel from "./ControlPanel";
import {Table} from "react-bootstrap";
import {useRequest} from "./Effects";

function Home() {
    const [apiInfo, initVersion] = useState({
        version: "",
        gdalVersion: ""
    })

    useRequest("/v1/about/version", initVersion)

    return (
        <div className="container-fluid">
            <header className="Header">
                <img src={njord} className="img-fluid w-25" alt="logo"/>
            </header>
            <div className="Center">
                <Table striped bordered hover variant="light" className="w-50">
                    <thead>
                    <tr>
                        <th colSpan="2">Njord ENC Server</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>UI Version</td>
                        <td>{process.env.REACT_APP_VERSION}</td>
                    </tr>
                    <tr>
                        <td>API Version</td>
                        <td>{apiInfo.version}</td>
                    </tr>
                    <tr>
                        <td>Gdal Version</td>
                        <td>{apiInfo.gdalVersion}</td>
                    </tr>
                    </tbody>
                </Table>
            </div>
        </div>
    );
}

const NoMatch = () => <header className="App Header">
    <p>Page not found!</p>
</header>

function App() {
    return (
        <div className="App">
            <NavBar/>
            <Routes>
                <Route path="/" element={<Outlet/>}>
                    <Route index element={<Home/>}/>
                    <Route path="enc" element={<Enc/>}/>
                    <Route path="control/:page" element={<ControlPanel/>}/>
                    <Route path="*" element={<NoMatch/>}/>
                </Route>
            </Routes>
        </div>
    );
}

export default App;
