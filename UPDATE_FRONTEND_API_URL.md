# Update Frontend to Use Render Backend

## ✅ Your Backend is Live on Render!

**Backend URL:** `https://studentrepository-backend.onrender.com`

## 🔧 Update Your Frontend Configuration

Your frontend is currently calling:
```
https://studentrepository-backend.onrender.com/student/add
```

But it should be calling:
```
https://studentrepository-backend.onrender.com/student/add
```

## Step 1: Find Your Frontend API Configuration

Look for where your API URL is defined. Common locations:

### Option A: Environment Variables (.env file)

Look for `.env`, `.env.production`, or `.env.local` in your frontend project:

```env
# ❌ OLD - Remove or update this
REACT_APP_API_URL=https://studentrepository-backend.onrender.com
# or
VITE_API_URL=https://studentrepository-backend.onrender.com

# ✅ NEW - Update to Render URL
REACT_APP_API_URL=https://studentrepository-backend.onrender.com
# or
VITE_API_URL=https://studentrepository-backend.onrender.com
```

### Option B: Config File

Look for files like:
- `src/config/api.js`
- `src/utils/api.js`
- `src/constants/index.js`
- `src/config/constants.js`

Update them like this:

```javascript
// ❌ OLD
export const API_BASE_URL = 'https://studentrepository-backend.onrender.com';

// ✅ NEW
export const API_BASE_URL = 'https://studentrepository-backend.onrender.com';
```

### Option C: Direct in Component (SignUp.jsx)

If the URL is hardcoded in your components, find `SignUp.jsx` around line 70:

```javascript
// ❌ OLD - Around line 70 in SignUp.jsx
const response = await fetch('https://studentrepository-backend.onrender.com/student/add', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify(formData)
});

// ✅ NEW - Update to Render URL
const response = await fetch('https://studentrepository-backend.onrender.com/student/add', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify(formData)
});
```

### Option D: API Service File

If you have an API service file (like `api.js` or `axios.js`):

```javascript
// ❌ OLD
const API = axios.create({
  baseURL: 'https://studentrepository-backend.onrender.com',
});

// ✅ NEW
const API = axios.create({
  baseURL: 'https://studentrepository-backend.onrender.com',
});
```

## Step 2: Verify All API Calls

Make sure ALL your API endpoints use the correct base URL:

- ✅ `/student/add` → `https://studentrepository-backend.onrender.com/student/add`
- ✅ `/student/login` → `https://studentrepository-backend.onrender.com/student/login`
- ✅ `/admin/getFaculties` → `https://studentrepository-backend.onrender.com/admin/getFaculties`
- ✅ `/lecturer/getAll` → `https://studentrepository-backend.onrender.com/lecturer/getAll`

**Important:** Don't add `/api` prefix! Your endpoints are directly under the root:
- ❌ `https://studentrepository-backend.onrender.com/api/student/add` (WRONG)
- ✅ `https://studentrepository-backend.onrender.com/student/add` (CORRECT)

## Step 3: Test Backend is Working

Before updating frontend, test your backend:

1. **Health Check:**
   ```
   https://studentrepository-backend.onrender.com/health
   ```
   Should return: `{"status":"UP","service":"studentrepository-backend"}`

2. **Root Endpoint:**
   ```
   https://studentrepository-backend.onrender.com/
   ```
   Should return API info

## Step 4: Update and Redeploy Frontend

1. **Update the API URL** in your frontend code (see Step 1)

2. **Commit changes:**
   ```bash
   git add .
   git commit -m "Update API URL to Render backend"
   git push origin main
   ```

3. **Vercel will auto-deploy** your frontend

4. **Test your application** after deployment

## Step 5: Verify CORS is Working

After updating, the CORS error should be resolved because:
- ✅ Your backend is on Render (`*.onrender.com` - already in CORS config)
- ✅ Your frontend is on Vercel (`*.vercel.app` - already in CORS config)
- ✅ All origins are properly configured

## 🔍 Search and Replace

If you have many files with the old URL, use search and replace in your IDE:

**Find:**
```
https://studentrepository-backend.onrender.com
```

**Replace with:**
```
https://studentrepository-backend.onrender.com
```

## 📝 Quick Checklist

- [ ] Found where API URL is configured in frontend
- [ ] Updated API URL to `https://studentrepository-backend.onrender.com`
- [ ] Removed any `/api` prefix from URLs
- [ ] Committed and pushed changes
- [ ] Frontend redeployed on Vercel
- [ ] Tested signup/login functionality

## 🚨 Still Getting CORS Errors?

If you still see CORS errors after updating:

1. **Hard refresh your browser:** `Ctrl+Shift+R` (Windows) or `Cmd+Shift+R` (Mac)
2. **Clear browser cache**
3. **Check browser console** for exact error message
4. **Verify backend is responding:** Test `https://studentrepository-backend.onrender.com/health` in browser
5. **Check Render logs** to ensure backend is running

## 🎉 You're All Set!

Once you update your frontend to use the Render backend URL, everything should work perfectly!

Your setup:
- **Frontend:** Vercel (`https://repository-react-iota.vercel.app`)
- **Backend:** Render (`https://studentrepository-backend.onrender.com`)
- **Database:** Railway (MySQL)

Perfect architecture! 🚀

