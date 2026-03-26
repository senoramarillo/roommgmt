import {Drawer, Input, Col, Select, Form, Row, Button, Spin} from 'antd';
import {addNewBuilding} from "./client";
import {LoadingOutlined} from "@ant-design/icons";
import {useState} from 'react';
import {errorNotification, successNotification} from "./Notification";

const {Option} = Select;
const antIcon = <LoadingOutlined style={{fontSize: 24}} spin/>;

/**
 * BuildingDrawerForm Component
 * 
 * A drawer form component for creating new buildings.
 * Handles form validation, API submission, and user notifications.
 * 
 * @component
 * @param {Object} props - Component props
 * @param {boolean} props.showDrawer - Controls visibility of the drawer
 * @param {Function} props.setShowDrawer - Function to update drawer visibility
 * @param {Function} props.fetchBuildings - Function to refresh the buildings list after creation
 * @returns {JSX.Element} The drawer form component
 */
function BuildingDrawerForm({showDrawer, setShowDrawer, fetchBuildings}) {
    const onClose = () => setShowDrawer(false);
    const [submitting, setSubmitting] = useState(false);

    /**
     * Handles form submission for creating a new building.
     * Sends building data to the backend and refreshes the buildings list on success.
     * 
     * @param {Object} building - The building form data
     */
    const onFinish = building => {
        setSubmitting(true)
        console.log(JSON.stringify(building, null, 2))
        addNewBuilding(building)
            .then(() => {
                console.log("building added")
                onClose();
                successNotification(
                    "Building successfully added",
                    `${building.name} was added to the system`
                )
                fetchBuildings();
            }).catch(err => {
            console.log(err);
            console.log(err);
            if (err.response?.headers.get('content-type')?.includes('application/json')) {
                err.response.json().then(res => {
                    console.log(res);
                    errorNotification(
                        "There was an issue",
                        `${res.message} [${res.status}] [${res.error}]`,
                        "bottomLeft"
                    )
                });
            } else {
                errorNotification(
                    "There was an issue",
                    `Request failed with status ${err.response?.status}`,
                    "bottomLeft"
                )
            }
        }).finally(() => {
            setSubmitting(false);
        })
    };

    /**
     * Handles form validation errors.
     * Displays alert with validation error information.
     * 
     * @param {Object} errorInfo - The validation error information
     */
    const onFinishFailed = errorInfo => {
        alert(JSON.stringify(errorInfo, null, 2));
    };

    return <Drawer
        title="Create new building"
        width={720}
        onClose={onClose}
        open={showDrawer}
        bodyStyle={{paddingBottom: 80}}
        footer={
            <div
                style={{
                    textAlign: 'right',
                }}
            >
                <Button onClick={onClose} style={{marginRight: 8}}>
                    Cancel
                </Button>
            </div>
        }
    >
        <Form layout="vertical"
              onFinishFailed={onFinishFailed}
              onFinish={onFinish}
              hideRequiredMark>
            <Row gutter={16}>
                <Col span={12}>
                    <Form.Item
                        name="buildingNumber"
                        label="Building Number"
                        rules={[{required: true, message: 'Please enter building number'}]}
                    >
                        <Input placeholder="Please enter building number"/>
                    </Form.Item>
                </Col>
                <Col span={12}>
                    <Form.Item
                        name="description"
                        label="Description"
                        rules={[{required: true, message: 'Please enter description'}]}
                    >
                        <Input placeholder="Please enter description"/>
                    </Form.Item>
                </Col>
            </Row>
            <Row gutter={16}>
                <Col span={12}>
                    <Form.Item
                        name="publicAccess"
                        label="Public Access"
                        rules={[{required: true, message: 'Please select public access'}]}
                    >
                        <Select placeholder="Please select public access">
                            <Option value="TRUE">TRUE</Option>
                            <Option value="FALSE">FALSE</Option>
                        </Select>
                    </Form.Item>
                </Col>
            </Row>
            <Row>
                <Col span={12}>
                    <Form.Item>
                        <Button type="primary" htmlType="submit">
                            Submit
                        </Button>
                    </Form.Item>
                </Col>
            </Row>
            <Row>
                {submitting && <Spin indicator={antIcon}/>}
            </Row>
        </Form>
    </Drawer>
}

export default BuildingDrawerForm;