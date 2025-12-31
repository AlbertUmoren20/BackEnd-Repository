# Quick Deployment Checklist for Render

## ✅ Pre-Deployment Checklist

- [x] `render.yaml` configured with Docker runtime
- [x] `Dockerfile` optimized for multi-stage build
- [x] `application.properties` uses environment variables
- [x] CORS configured for Render domains (`*.onrender.com`)
- [x] Port configuration uses `${PORT}` (automatic in Render)

## 📋 Deployment Steps

### 1. Push Code to Repository
```bash
git add .
git commit -m "Configure for Render deployment"
git push origin main
```

### 2. Create Render Web Service
1. Go to https://dashboard.render.com
2. Click "New +" → "Web Service"
3. Connect your Git repository
4. Render will auto-detect `render.yaml`

### 3. Configure Environment Variables in Render Dashboard
Go to your service → Environment → Add these:

**Required:**
- `SPRING_DATASOURCE_PASSWORD` = `MYdxycIXVwUoHNXnXkWnOTAgqVXvbkXV` (Set as **Secret**)

**Already configured in render.yaml:**
- `SPRING_PROFILES_ACTIVE` = `production`
- `SPRING_DATASOURCE_URL` = (already set)
- `SPRING_DATASOURCE_USERNAME` = `root`
- `SPRING_JPA_HIBERNATE_DDL_AUTO` = `update`
- `SPRING_JPA_SHOW_SQL` = `false`
- `SPRING_JPA_OPEN_IN_VIEW` = `false`

### 4. Verify Settings
- **Name:** `studentrepository-backend`
- **Region:** Choose closest to your users
- **Plan:** Start with Free (upgrade if needed)
- **Auto-Deploy:** Enable for automatic deployments

### 5. Deploy
- Click "Create Web Service"
- Monitor build logs
- Wait for deployment (typically 5-10 minutes)

### 6. Get Your Backend URL
After deployment, your backend will be available at:
```
https://studentrepository-backend.onrender.com
```

### 7. Update Frontend
Update your Vercel frontend to use the new Render backend URL:
```javascript
// Example API configuration
const API_URL = 'https://studentrepository-backend.onrender.com';
```

## 🔍 Testing After Deployment

1. **Health Check:** Visit your Render URL in browser
2. **API Test:** Test a simple endpoint like `/student/getAll`
3. **CORS Test:** Make API calls from your frontend
4. **Database:** Verify database connections are working

## ⚠️ Important Notes

### Free Plan Limitations
- Service sleeps after 15 minutes of inactivity
- First request after sleep may take 30-60 seconds (cold start)
- Consider upgrading to Starter plan ($7/month) for always-on service

### Database Connection
- Your Railway MySQL database should be accessible from Render
- If connection fails, check Railway firewall/whitelist settings
- Verify Railway database is running and accessible

### Environment Variables
- Never commit passwords to git
- Always use Render's Secret environment variables for sensitive data
- The password in `render.yaml` is marked `sync: false` - configure it manually

## 🚀 Next Steps

1. Deploy and test
2. Monitor logs for any errors
3. Update frontend API URLs
4. Test all endpoints
5. Consider upgrading plan if needed for production

## 📞 Troubleshooting

- **Build fails:** Check build logs in Render dashboard
- **App won't start:** Check runtime logs, verify environment variables
- **Database errors:** Verify Railway database is accessible, check credentials
- **CORS errors:** Verify CORS configuration includes your frontend URL

## 📚 Documentation
See `RENDER_DEPLOYMENT.md` for detailed documentation.

