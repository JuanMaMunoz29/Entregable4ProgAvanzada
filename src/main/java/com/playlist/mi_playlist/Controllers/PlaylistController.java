package com.playlist.mi_playlist.Controllers;

import com.playlist.mi_playlist.Clases.Video;
import com.playlist.mi_playlist.Repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@Controller
public class PlaylistController {

    @Autowired
    private VideoRepository videoRepository;

    @GetMapping("/playlist")
    public String showPlaylist(Model model, HttpSession session) {
        model.addAttribute("videos", videoRepository.findAll());

        // Obtener set de likes de la sesión (ids de videos que este usuario/cliente ya likeó)
        @SuppressWarnings("unchecked")
        Set<Long> liked = (Set<Long>) session.getAttribute("likedVideos");
        if (liked == null) {
            liked = new HashSet<>();
            // No guardamos inmediatamente para evitar persistir sets vacíos innecesarios,
            // pero es seguro guardarlo aquí también si se prefiere.
            session.setAttribute("likedVideos", liked);
        }
        model.addAttribute("likedIds", liked);

        return "playlist";
    }

    @PostMapping("/playlist/add")
    public String addVideo(@RequestParam String title,
                           @RequestParam String url) {

        String embedUrl = toEmbedUrl(url);
        videoRepository.save(new Video(title, embedUrl));
        return "redirect:/playlist";
    }

    @PostMapping("/playlist/delete")
    public String deleteVideo(@RequestParam Long id) {
        videoRepository.deleteById(id);
        return "redirect:/playlist";
    }

    @PostMapping("/playlist/like")
    public String likeVideo(@RequestParam Long id, HttpSession session) {
        Video v = videoRepository.findById(id).orElse(null);
        if (v != null) {
            @SuppressWarnings("unchecked")
            Set<Long> liked = (Set<Long>) session.getAttribute("likedVideos");
            if (liked == null) {
                liked = new HashSet<>();
            }

            if (liked.contains(id)) {
                // Si ya estaba likeado por esta sesión, quitar like
                int newLikes = Math.max(0, v.getLikes() - 1);
                v.setLikes(newLikes);
                liked.remove(id);
            } else {
                // Si no estaba likeado, poner like
                v.setLikes(v.getLikes() + 1);
                liked.add(id);
            }

            session.setAttribute("likedVideos", liked);
            videoRepository.save(v);
        }
        return "redirect:/playlist";
    }

    @PostMapping("/playlist/favorite")
    public String toggleFavorite(@RequestParam Long id) {
        Video v = videoRepository.findById(id).orElse(null);
        if (v != null) {
            v.setFavorite(!v.isFavorite());
            videoRepository.save(v);
        }
        return "redirect:/playlist";
    }

    private String toEmbedUrl(String url) {
        if (url == null) return null;

        if (url.contains("youtu.be/")) {
            int idx = url.lastIndexOf('/');
            String idPart = url.substring(idx + 1);
            int q = idPart.indexOf('?');
            if (q != -1) idPart = idPart.substring(0, q);
            return "https://www.youtube.com/embed/" + idPart;
        }

        if (url.contains("watch?v=")) {
            int idx = url.indexOf("watch?v=") + "watch?v=".length();
            String idPart = url.substring(idx);
            int amp = idPart.indexOf('&');
            if (amp != -1) idPart = idPart.substring(0, amp);
            int q = idPart.indexOf('?');
            if (q != -1) idPart = idPart.substring(0, q);
            return "https://www.youtube.com/embed/" + idPart;
        }

        return url;
    }
}
