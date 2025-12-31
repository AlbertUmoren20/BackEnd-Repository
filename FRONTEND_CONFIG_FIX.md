# Frontend Configuration Fix for Render Backend

## ⚠️ Issue Identified

Your frontend is currently calling:
```
https://back-end-repository.vercel.app/student/add
```

But your backend is now deployed on **Render**, not Vercel!

## ✅ Solution: Update Frontend API URL

### Step 1: Find Your Render Backend URL

Go to your Render dashboard and find your backend service URL. It should look like:
```
https://studentrepository-backend.onrender.com
```
or
```
https://your-app-name.onrender.com
```

### Step 2: Update Frontend Configuration

Update your frontend API configuration file. Look for files like:
- `.env`
- `.env.production`
- `config.js`
- `api.js`
- `constants.js`
- Or wherever you store your API base URL

**Find this line:**
```javascript
// ❌ OLD - Vercel backend (doesn't exist or not working)
const API_URL = 'https://back-end-repository.vercel.app';
// or
const API_BASE_URL = 'https://back-end-repository.vercel.app';
```

**Replace with your Render URL:**
```javascript
// ✅ NEW - Render backend
const API_URL = 'https://studentrepository-backend.onrender.com';
// or whatever your Render service name is
```

### Step 3: Common Frontend File Locations

Check these common locations:

#### If using React/Vite:
```javascript
// src/config/api.js or src/utils/api.js
export const API_BASE_URL = 'https://studentrepository-backend.onrender.com';
```

#### If using environment variables (.env):
```env
# .env.production
REACT_APP_API_URL=https://studentrepository-backend.onrender.com
# or
VITE_API_URL=https://studentrepository-backend.onrender.com
```

#### If hardcoded in components:
```javascript
// SignUp.jsx or similar
const response = await fetch('https://studentrepository-backend.onrender.com/student/add', {
  // ...
});
```

### Step 4: Verify No `/api` Prefix

Make sure you're NOT adding `/api` to the URL:

```javascript
// ❌ WRONG
const API_URL = 'https://studentrepository-backend.onrender.com/api';

// ✅ CORRECT
const API_URL = 'https://studentrepository-backend.onrender.com';
```

Your endpoints will be:
- `https://studentrepository-backend.onrender.com/student/add`
- `https://studentrepository-backend.onrender.com/student/login`
- `https://studentrepository-backend.onrender.com/admin/getFaculties`
- etc.

### Step 5: Test Your Frontend

After updating:

1. **Rebuild your frontend** (if needed)
2. **Redeploy to Vercel** (if using CI/CD, it should auto-deploy)
3. **Test the signup form again**

## 🔍 Finding Your Render Backend URL

1. Go to https://dashboard.render.com
2. Click on your backend service (e.g., `studentrepository-backend`)
3. Look at the top - you'll see your service URL
4. Copy that URL (should end with `.onrender.com`)

## 📝 Quick Checklist

- [ ] Found your Render backend URL (e.g., `https://xxx.onrender.com`)
- [ ] Updated frontend API configuration file
- [ ] Removed any `/api` prefix from the URL
- [ ] Tested that `/health` endpoint works: `https://your-render-url.onrender.com/health`
- [ ] Redeployed frontend to Vercel
- [ ] Tested signup/login functionality

## 🚨 Alternative: If You Want to Keep Using Vercel Backend

If you prefer to use Vercel for backend (though it has limitations for Spring Boot):

1. Your Vercel backend might not be deployed or working correctly
2. Check Vercel deployment status
3. Ensure `vercel.json` is correct (we fixed it earlier)
4. Redeploy to Vercel

**However, Render is recommended** for Spring Boot applications as it's more stable and better suited for long-running applications.

## 📞 Still Having Issues?

1. Share your Render backend URL
2. Share which file you updated in the frontend
3. Share any new error messages from the browser console

