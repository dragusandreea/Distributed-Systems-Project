FROM node:18.18.2 AS build
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build

FROM nginx:alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /app/dist/energy-management-system-frontend/ /usr/share/nginx/html
EXPOSE 4200

