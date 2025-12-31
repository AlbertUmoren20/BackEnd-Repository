# Render Deployment Guide

This guide will help you deploy your Spring Boot application to Render.

## Prerequisites

1. A Render account (sign up at https://render.com)
2. Your code pushed to a Git repository (GitHub, GitLab, or BitBucket)
3. Railway database credentials (for database connection)

## Step 1: Create a Render Web Service

1. Log in to your Render dashboard
2. Click "New +" and select "Web Service"
3. Connect your Git repository
4. Render will auto-detect the `render.yaml` file

## Step 2: Configure Environment Variables

In your Render dashboard, go to your service → Environment → Add Environment Variable:

**Required Environment Variables:**

```
SPRING_DATASOURCE_URL=jdbc:mysql://turntable.proxy.rlwy.net:55320/e-repository?useSSL=true&requireSSL=true&serverTimezone=UTC&allowPublicKeyRetrieval=true
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=MYdxycIXVwUoHNXnXkWnOTAgqVXvbkXV
SPRING_PROFILES_ACTIVE=production
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=false
SPRING_JPA_OPEN_IN_VIEW=false
```

**Note:** For security, set `SPRING_DATASOURCE_PASSWORD` as a "Secret" in Render.

## Step 3: Configure Service Settings

In the Render dashboard:

1. **Name:** `studentrepository-backend` (or your preferred name)
2. **Region:** Choose closest to your users (e.g., Oregon, Frankfurt)
3. **Branch:** `main` (or your production branch)
4. **Root Directory:** Leave blank (uses root)
5. **Build Command:** `mvn clean package -DskipTests` (already in render.yaml)
6. **Start Command:** `java -jar target/studentrepository-0.0.1-SNAPSHOT.jar` (already in render.yaml)
7. **Plan:** Start with Free, upgrade if needed

## Step 4: Deploy

1. Click "Create Web Service"
2. Render will:
   - Clone your repository
   - Build your application using Docker
   - Deploy it
3. Monitor the build logs for any issues

## Step 5: Get Your Backend URL

After successful deployment, Render will provide a URL like:
- `https://studentrepository-backend.onrender.com`

## Step 6: Update Frontend API URL

Update your frontend (Vercel) to point to your Render backend URL:

```javascript
// In your frontend configuration
const API_BASE_URL = 'https://studentrepository-backend.onrender.com';
```

## Troubleshooting

### Build Fails
- Check build logs in Render dashboard
- Ensure `pom.xml` is valid
- Verify Java 17 is supported (it is in the Dockerfile)

### Application Won't Start
- Check runtime logs in Render dashboard
- Verify environment variables are set correctly
- Ensure PORT environment variable is used (already configured)

### Database Connection Issues
- Verify Railway database is accessible from Render's IPs
- Check Railway firewall/whitelist settings
- Verify database credentials in environment variables

### CORS Errors
- Backend CORS is already configured for:
  - `*.vercel.app` (your frontend)
  - `*.onrender.com` (Render deployments)
  - `localhost` (local development)

## Environment-Specific Configuration

The application uses environment variables with fallback values:
- `SPRING_DATASOURCE_URL` - Database connection URL
- `SPRING_DATASOURCE_USERNAME` - Database username
- `SPRING_DATASOURCE_PASSWORD` - Database password (set as secret)
- `PORT` - Server port (automatically set by Render)
- `SPRING_PROFILES_ACTIVE` - Spring profile (set to "production")

## Health Checks (Optional)

To enable health checks, add Spring Boot Actuator:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Then configure in `application.properties`:
```properties
management.endpoints.web.exposure.include=health
management.endpoint.health.show-details=when-authorized
```

Update `healthCheckPath` in `render.yaml` to `/actuator/health`.

## Scaling

- **Free Plan:** Sleeps after 15 minutes of inactivity
- **Starter Plan:** Always on, better for production
- **Standard Plan:** Auto-scaling, better performance

## Security Recommendations

1. Set all sensitive values (passwords, API keys) as "Secret" environment variables in Render
2. Use Render's environment variable sync feature for team management
3. Enable HTTPS (automatically provided by Render)
4. Regularly update dependencies for security patches

## Monitoring

- Check logs in Render dashboard
- Set up alerts for deployment failures
- Monitor database connections (Railway dashboard)

## Next Steps

1. Test all API endpoints after deployment
2. Verify CORS is working from your frontend
3. Monitor performance and upgrade plan if needed
4. Set up custom domain (optional)

