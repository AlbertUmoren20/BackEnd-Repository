# CORS 404 Error Troubleshooting Guide

## Understanding the Error

A **CORS 404 Error** typically means:
1. The endpoint you're calling doesn't exist (404 Not Found)
2. The browser blocks the response due to CORS policy
3. The frontend is calling the wrong URL or path

## Step 1: Verify Your Backend is Running

### Test Health Endpoints

After deployment, test these endpoints in your browser or Postman:

1. **Root endpoint:**
   ```
   https://your-app-name.onrender.com/
   ```
   Should return: `{"status":"UP","message":"Student Repository API is running",...}`

2. **Health endpoint:**
   ```
   https://your-app-name.onrender.com/health
   ```
   Should return: `{"status":"UP","service":"studentrepository-backend"}`

3. **Status endpoint:**
   ```
   https://your-app-name.onrender.com/api/status
   ```

### If These Don't Work:

1. **Check Render Logs:**
   - Go to Render Dashboard → Your Service → Logs
   - Look for startup errors or exceptions

2. **Verify Deployment:**
   - Check that the service status is "Live" (not "Deploying" or "Failed")
   - Wait 2-3 minutes after deployment for full startup

3. **Check Application Logs:**
   - Look for database connection errors
   - Look for port binding errors

## Step 2: Verify Frontend API Configuration

### Check Your Frontend Base URL

Your frontend should be configured with the **full Render URL**:

```javascript
// ✅ CORRECT
const API_BASE_URL = 'https://studentrepository-backend.onrender.com';

// ❌ WRONG - Missing protocol
const API_BASE_URL = 'studentrepository-backend.onrender.com';

// ❌ WRONG - Missing domain
const API_BASE_URL = '/api';
```

### Verify Endpoint Paths

Your endpoints should be:
```
GET  https://your-app.onrender.com/student/getAll
POST https://your-app.onrender.com/student/login
POST https://your-app.onrender.com/admin/addFaculty
GET  https://your-app.onrender.com/lecturer/getAll
```

**NOT:**
```
❌ https://your-app.onrender.com/api/student/getAll
❌ https://your-app.onrender.com/studentrepository-backend/student/getAll
```

## Step 3: Test API Endpoints Directly

### Using Browser (Simple GET requests):

```bash
# Test health
https://your-app-name.onrender.com/health

# Test student endpoint
https://your-app-name.onrender.com/student/getAll
```

### Using cURL:

```bash
# Test health
curl https://your-app-name.onrender.com/health

# Test with CORS headers
curl -X GET \
  https://your-app-name.onrender.com/student/getAll \
  -H "Origin: https://repository-react-iota.vercel.app" \
  -v
```

### Using Postman/Thunder Client:

1. Create a new request
2. Set method to GET
3. Set URL to: `https://your-app-name.onrender.com/student/getAll`
4. Add header: `Origin: https://repository-react-iota.vercel.app`
5. Send request

## Step 4: Check Browser Console

Open your browser's Developer Tools (F12) → Console tab:

### Common Errors:

1. **CORS policy error:**
   ```
   Access to fetch at 'https://...' from origin 'https://...' has been blocked by CORS policy
   ```
   **Solution:** Check that your frontend URL is in the CORS allowed origins

2. **404 Not Found:**
   ```
   GET https://your-app.onrender.com/api/student/getAll 404 (Not Found)
   ```
   **Solution:** Remove `/api` prefix, use direct path: `/student/getAll`

3. **Network Error:**
   ```
   Failed to fetch
   NetworkError when attempting to fetch resource
   ```
   **Solution:** 
   - Check if Render service is running
   - Check if URL is correct
   - Verify HTTPS is used (not HTTP)

## Step 5: Verify CORS Configuration

### Check Allowed Origins

The backend is configured to allow:
- `https://*.vercel.app` (all Vercel apps)
- `https://*.onrender.com` (all Render apps)
- `http://localhost:*` (local development)

### Test CORS Preflight

For POST/PUT/DELETE requests, the browser sends an OPTIONS request first:

```bash
curl -X OPTIONS \
  https://your-app-name.onrender.com/student/login \
  -H "Origin: https://repository-react-iota.vercel.app" \
  -H "Access-Control-Request-Method: POST" \
  -H "Access-Control-Request-Headers: Content-Type" \
  -v
```

**Expected Response Headers:**
```
Access-Control-Allow-Origin: https://repository-react-iota.vercel.app
Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS, PATCH, HEAD
Access-Control-Allow-Headers: *
Access-Control-Allow-Credentials: true
```

## Step 6: Common Issues & Solutions

### Issue 1: Service is Sleeping (Free Plan)

**Symptoms:**
- First request takes 30-60 seconds
- Subsequent requests are fast

**Solution:**
- Wait for the first request to wake up the service
- Consider upgrading to Starter plan ($7/month) for always-on service

### Issue 2: Wrong API URL in Frontend

**Symptoms:**
- 404 errors
- Network errors

**Solution:**
- Double-check your frontend `.env` or config file
- Use full URL: `https://studentrepository-backend.onrender.com`
- Don't add `/api` prefix

### Issue 3: Database Connection Issues

**Symptoms:**
- App starts but endpoints return errors
- Check Render logs for database connection errors

**Solution:**
- Verify Railway database is accessible
- Check environment variables are set correctly
- Verify database credentials in Render dashboard

### Issue 4: Port Binding Issues

**Symptoms:**
- Service fails to start
- Port already in use errors

**Solution:**
- Verify `server.port=${PORT:8080}` in `application.properties`
- Render automatically provides `PORT` environment variable

## Step 7: Debugging Checklist

- [ ] Backend service is "Live" in Render dashboard
- [ ] Health endpoint (`/health`) returns 200 OK
- [ ] Frontend API base URL is correct (full HTTPS URL)
- [ ] No `/api` prefix in frontend API calls
- [ ] Browser console shows the actual error message
- [ ] CORS preflight (OPTIONS) requests return 200 OK
- [ ] Environment variables are set in Render dashboard
- [ ] Database connection is working (check logs)

## Quick Test Script

Save this as `test-api.html` and open in browser:

```html
<!DOCTYPE html>
<html>
<head>
    <title>API Test</title>
</head>
<body>
    <h1>Backend API Test</h1>
    <button onclick="testHealth()">Test Health</button>
    <button onclick="testStudents()">Test Students</button>
    <pre id="result"></pre>
    
    <script>
        const API_URL = 'https://your-app-name.onrender.com'; // Replace with your URL
        
        async function testHealth() {
            try {
                const response = await fetch(`${API_URL}/health`);
                const data = await response.json();
                document.getElementById('result').textContent = JSON.stringify(data, null, 2);
            } catch (error) {
                document.getElementById('result').textContent = 'Error: ' + error.message;
            }
        }
        
        async function testStudents() {
            try {
                const response = await fetch(`${API_URL}/student/getAll`);
                const data = await response.json();
                document.getElementById('result').textContent = JSON.stringify(data, null, 2);
            } catch (error) {
                document.getElementById('result').textContent = 'Error: ' + error.message;
            }
        }
    </script>
</body>
</html>
```

## Still Having Issues?

1. **Share the exact error message** from browser console
2. **Share your frontend API configuration** (without sensitive data)
3. **Share Render logs** (last 50 lines)
4. **Share the exact URL** your frontend is calling

