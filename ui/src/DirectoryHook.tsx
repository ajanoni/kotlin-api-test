import { useState } from "react"
import axios from 'axios';

interface FsResult {
    items: FsItem[];
    totalItems: number;
    totalSize: number;
}

interface FsItem {
    name: string;
    size: number;
    lastModification: Date;
}

export default (): [(id: string) => void, FsResult, string] => {
    const initialState = { items: [], totalItems:0, totalSize: 0 };
    const [fsResult, setFsResult] = useState<FsResult>(initialState);
    const [errorMsg, setErrorMsg] = useState("");

    const getDirectoryList = async (path: string) => {
            axios.get(`http://localhost:8080/directory?directoryPath=${path}`)
                .then(res => {
                    setFsResult(res.data);
                })
                .catch((error) => {
                    if (error.response && error.response.data) {
                        setErrorMsg(error.response.data.messages);
                    } else {
                        setErrorMsg("Unexpected error");
                    }
                    setFsResult(initialState);
                });

    };

    return [getDirectoryList, fsResult, errorMsg];
};

