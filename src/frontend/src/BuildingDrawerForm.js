import {Drawer, Input, Col, Select, Form, Row, Button, Spin} from 'antd';
import {addNewBuilding} from "./client";
import {LoadingOutlined} from "@ant-design/icons";
import {useState} from 'react';
import {errorNotification, successNotification} from "./Notification";

const {Option} = Select;
const antIcon = <LoadingOutlined style={{fontSize: 24}} spin/>;

function BuildingDrawerForm({showDrawer, setShowDrawer, fetchBuildings}) {
    const onCLose = () => setShowDrawer(false);
    const [submitting, setSubmitting] = useState(false);

    const onFinish = building => {
        setSubmitting(true)
        console.log(JSON.stringify(building, null, 2))
        addNewBuilding(building)
            .then(() => {
                console.log("building added")
                onCLose();
                successNotification(
                    "Building successfully added",
                    `${building.name} was added to the system`
                )
                fetchBuildings();
            }).catch(err => {
            console.log(err);
            err.response.json().then(res => {
                console.log(res);
                errorNotification(
                    "There was an issue",
                    `${res.message} [${res.status}] [${res.error}]`,
                    "bottomLeft"
                )
            });
        }).finally(() => {
            setSubmitting(false);
        })
    };

    const onFinishFailed = errorInfo => {
        alert(JSON.stringify(errorInfo, null, 2));
    };

    return <Drawer
        title="Create new building"
        width={720}
        onClose={onCLose}
        visible={showDrawer}
        bodyStyle={{paddingBottom: 80}}
        footer={
            <div
                style={{
                    textAlign: 'right',
                }}
            >
                <Button onClick={onCLose} style={{marginRight: 8}}>
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