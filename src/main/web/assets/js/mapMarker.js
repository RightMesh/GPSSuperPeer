$(document).ready(() => {
    $.get("/creds", (creds) => {
        const platform = new H.service.Platform({
            app_id: creds.appId,
            app_code: creds.appCode,
            useCIT: true,
            useHTTPS: true
        });
        const defaultLayers = platform.createDefaultLayers();

        // Init map
        const map = new H.Map(document.getElementById('map'),
            defaultLayers.normal.map, {
                center: {
                    lat: 49.2074405,
                    lng: -122.666803
                },
                zoom: 15
            });

        new H.mapevents.Behavior(new H.mapevents.MapEvents(map));

        // Create the default UI components
        H.ui.UI.createDefault(map, defaultLayers);
        markMap(map);
    });
});

const markMap = (map) => {
    $.get("/map/coords", (coords) => {
        coords.forEach((item) =>
            map.addObject(new H.map.Marker({
                lat: item.lat,
                lng: item.long
            })))
    });
}