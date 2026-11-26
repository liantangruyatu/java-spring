const axios = require('axios');

// 设置API基本URL
const apiUrl = 'http://localhost:8080/api';

// 测试教师列表API
async function testTeacherList() {
    console.log('=== 测试教师列表API ===');
    try {
        const response = await axios.get(`${apiUrl}/teacher/list`);
        console.log('教师列表API响应状态:', response.status);
        console.log('教师列表数据:', response.data);
        console.log('教师数量:', response.data.data?.length || 0);
        if (response.data.data && response.data.data.length > 0) {
            console.log('第一个教师详情:', response.data.data[0]);
        }
        console.log('=== 教师列表API测试完成 ===\n');
    } catch (error) {
        console.error('教师列表API测试失败:', error.message);
        console.error('错误详情:', error.response ? error.response.data : error);
        console.log('=== 教师列表API测试完成 ===\n');
    }
}

// 测试学生列表API
async function testStudentList() {
    console.log('=== 测试学生列表API ===');
    try {
        const response = await axios.get(`${apiUrl}/student/list`);
        console.log('学生列表API响应状态:', response.status);
        console.log('学生列表数据:', response.data);
        console.log('学生数量:', response.data.data?.length || 0);
        if (response.data.data && response.data.data.length > 0) {
            console.log('第一个学生详情:', response.data.data[0]);
        }
        console.log('=== 学生列表API测试完成 ===\n');
    } catch (error) {
        console.error('学生列表API测试失败:', error.message);
        console.error('错误详情:', error.response ? error.response.data : error);
        console.log('=== 学生列表API测试完成 ===\n');
    }
}

// 测试选题列表API
async function testSelectionList() {
    console.log('=== 测试选题列表API ===');
    try {
        const response = await axios.get(`${apiUrl}/selection/list`);
        console.log('选题列表API响应状态:', response.status);
        console.log('选题列表数据:', response.data);
        console.log('选题数量:', response.data.data?.length || 0);
        if (response.data.data && response.data.data.length > 0) {
            console.log('第一个选题详情:', response.data.data[0]);
        }
        console.log('=== 选题列表API测试完成 ===\n');
    } catch (error) {
        console.error('选题列表API测试失败:', error.message);
        console.error('错误详情:', error.response ? error.response.data : error);
        console.log('=== 选题列表API测试完成 ===\n');
    }
}

// 执行所有测试
async function runAllTests() {
    console.log('开始测试所有模块API...\n');
    await testTeacherList();
    await testStudentList();
    await testSelectionList();
    console.log('所有模块API测试完成!');
}

// 启动测试
runAllTests();
