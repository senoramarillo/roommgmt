import {useState, useEffect} from 'react';
import {deleteBuilding, getAllBuildings} from "./client";

import {
    Layout,
    Menu,
    Breadcrumb,
    Table,
    Spin,
    Empty,
    Button,
    Tag,
    Badge,
    Radio,
    Popconfirm
} from 'antd';

import {
    DesktopOutlined,
    HomeOutlined,
    MenuOutlined,
    UserOutlined,
    LoadingOutlined, PlusOutlined
} from '@ant-design/icons';

import BuildingDrawerForm from "./BuildingDrawerForm";
import './App.css';
import {errorNotification, successNotification} from "./Notification";

const {Header, Content, Sider} = Layout;
const {SubMenu} = Menu;

/**
 * Removes a building and refreshes the buildings list.
 * Handles API errors with optional chaining for safe response parsing.
 *
 * @param {number} buildingId the ID of the building to delete
 * @param {Function} callback function to refresh the buildings list after deletion
 */
const removeBuilding = (buildingId, callback) => {
    deleteBuilding(buildingId).then(() => {
        successNotification("Building deleted", `Building with ${buildingId} was deleted`);
        callback();
    }).catch(error => {
        console.log(error.response)
        if (error.response?.headers.get('content-type')?.includes('application/json')) {
            error.response.json().then(response => {
                console.log(response);
                errorNotification(
                    "There was an issue",
                    `${response.message} [${response.status}] [${response.error}]`
                )
            });
        } else {
            errorNotification(
                "There was an issue",
                `Request failed with status ${error.response?.status}`
            )
        }
    })
}

/**
 * Defines the table columns for the buildings table.
 * Includes columns for ID, Building Number, Description, Public Access, and Actions.
 *
 * @param {Function} fetchBuildings callback function to refresh buildings after an action
 * @returns {Array} array of column configuration objects for the Ant Design Table
 */
const columns = fetchBuildings => [
    {
        title: 'Id',
        dataIndex: 'id',
        key: 'id',
    },
    {
        title: 'Building Number',
        dataIndex: 'buildingNumber',
        key: 'buildingNumber',
    },
    {
        title: 'Description',
        dataIndex: 'description',
        key: 'description',
    },
    {
        title: 'Public Access',
        dataIndex: 'publicAccess',
        key: 'publicAccess',
        width: '20%',
        render : (text) => String(text),
    },
    {
        title: 'Actions',
        key: 'actions',
        render: (text, building) =>
            <Radio.Group>
                <Popconfirm
                    placement='topRight'
                    title={`Are you sure to delete ${building.description}`}
                    onConfirm={() => removeBuilding(building.id, fetchBuildings)}
                    okText='Yes'
                    cancelText='No'>
                    <Radio.Button value="small">Delete</Radio.Button>
                </Popconfirm>
                <Radio.Button onClick={() => alert("TODO: Implement edit building")} value="small">Edit</Radio.Button>
            </Radio.Group>
    }
];

const antIcon = <LoadingOutlined style={{fontSize: 24}} spin/>;

/**
 * Main App Component
 * 
 * Displays the Room Management System layout with a sidebar menu and buildings management interface.
 * Manages the state for buildings list, drawer visibility, and loading state.
 * Provides functionality to view, add, edit, and delete buildings.
 *
 * @component
 * @returns {JSX.Element} The main application layout
 */
function App() {
    const [buildings, setBuildings] = useState([]);
    const [collapsed, setCollapsed] = useState(false);
    const [fetching, setFetching] = useState(true);
    const [showDrawer, setShowDrawer] = useState(false);

    /**
     * Fetches all buildings from the backend API.
     * Updates the buildings state and handles loading and error states.
     */
    const fetchBuildings = () =>
        getAllBuildings()
            .then(response => response.json())
            .then(data => {
                console.log(data);
                setBuildings(data);
            }).catch(error => {
            console.log(error.response)
            error.response.json().then(response => {
                console.log(response);
                errorNotification(
                    "There was an issue",
                    `${response.message} [${response.status}] [${response.error}]`
                )
            });
        }).finally(() => setFetching(false))

    /**
     * Effect hook to fetch buildings on component mount.
     */
    useEffect(() => {
        console.log("component is mounted");
        fetchBuildings();
    }, []);

    /**
     * Renders the buildings content based on loading and data state.
     * Shows loading spinner, empty state, or buildings table with actions.
     *
     * @returns {JSX.Element} the rendered content
     */
    const renderBuildings = () => {
        if (fetching) {
            return <Spin indicator={antIcon}/>
        }

        if (buildings.length <= 0) {
            return <>
                <Button
                    onClick={() => setShowDrawer(!showDrawer)}
                    type="primary" shape="round" icon={<PlusOutlined/>} size="small">
                    Add New Building
                </Button>
                <BuildingDrawerForm
                    showDrawer={showDrawer}
                    setShowDrawer={setShowDrawer}
                    fetchBuildings={fetchBuildings}
                />
                <Empty/>
            </>
        }

        return <>
            <BuildingDrawerForm
                showDrawer={showDrawer}
                setShowDrawer={setShowDrawer}
                fetchBuildings={fetchBuildings}
            />

            <Table
                dataSource={buildings}
                columns={columns(fetchBuildings)}
                bordered
                title={() =>
                    <>
                        <Tag>Number of buildings</Tag>
                        <Badge count={buildings.length} className="site-badge-count-4"/>
                        <br/><br/>
                        <Button
                            onClick={() => setShowDrawer(!showDrawer)}
                            type="primary" shape="round" icon={<PlusOutlined/>} size="small">
                            Add New Building
                        </Button>
                    </>
                }
                pagination={{pageSize: 50}}
                scroll={{y: 500}}
                rowKey={(building) => building.id}
            />
        </>
    }

    return <Layout style={{minHeight: '100vh'}}>
        <Sider collapsible
               collapsed={collapsed}
               onCollapse={() => setCollapsed(!collapsed)}
               style={{
                   overflow: "auto",
                   height: "100vh",
                   position: "sticky",
                   top: 0,
                   left: 0
               }}>
            <div className="logo"/>
            <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline">
                <Menu.Item key="1" icon={<HomeOutlined/>}>
                    Rooms
                </Menu.Item>
                <Menu.Item key="2" icon={<DesktopOutlined/>}>
                    Meetings
                </Menu.Item>
                <SubMenu key="sub1" icon={<UserOutlined/>} title="User">
                    <Menu.Item key="3">Admin</Menu.Item>
                </SubMenu>
                <SubMenu key="sub2" icon={<MenuOutlined/>} title="Menu">
                    <Menu.Item key="6">Configuration</Menu.Item>
                </SubMenu>
            </Menu>
        </Sider>
        <Layout className="site-layout">
            <Header className="site-layout-background" style={{padding: 0}}/>
            <Content style={{margin: '0 16px'}}>
                <Breadcrumb style={{margin: '16px 0'}}>
                    <Breadcrumb.Item>Buildings</Breadcrumb.Item>
                    <Breadcrumb.Item>List</Breadcrumb.Item>
                </Breadcrumb>
                <div className="site-layout-background" style={{padding: 24, minHeight: 360}}>
                    {renderBuildings()}
                </div>
            </Content>
        </Layout>
    </Layout>
}

export default App;

