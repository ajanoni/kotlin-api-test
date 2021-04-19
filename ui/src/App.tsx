import "./App.scss";
import useTest from "./DirectoryHook";
import {useState} from "react";
import moment from "moment";
import { GoSearch } from "react-icons/go";

export default function App() {
    const [path, setPath] = useState('');
    const [getDirectoryList, fsResult, errorMessage] = useTest();
    const handleClickEvent = () => {
        getDirectoryList(path);
    }
    return (
        <div className="App">
            <div className="webflow-style-input">
                <input type="text"
                       value={path}
                       name="path"
                       placeholder="directory path"
                       onChange={(e) => setPath(e.target.value)} id="path"/>

                <button onClick={() => handleClickEvent()}><GoSearch/></button>
            </div>
            {(() => {
                if (errorMessage) {
                    return (
                        <div>
                            <p>{errorMessage}</p>
                        </div>
                    )
                }
            })()}
            <div className="table-wrapper">
                <table className="fl-table">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Size (Bytes)</th>
                        <th>Last Modification</th>
                    </tr>
                    </thead>
                    <tbody>
                    {fsResult.items.length > 0 ? (
                            fsResult.items.map(i => (
                                <tr>
                                    <td>{i["name"]}</td>
                                    <td>{i["size"]}</td>
                                    <td>{moment(i["lastModification"]).format("YYYY-MM-DD HH:mm:ss")}</td>
                                </tr>
                            ))
                        )
                        : (
                            <tr>
                                <td colSpan={3}>Empty directory.</td>
                            </tr>
                        )}

                    </tbody>
                </table>
                    {(() => {
                        if (fsResult.items.length > 0) {
                            return (
                                <div>
                                    <p>Total Items: {fsResult.totalItems}</p>
                                    <p>Total Size(Bytes): {fsResult.totalSize}</p>
                                </div>
                            )
                        }
                    })()}
            </div>
        </div>
    );
}
