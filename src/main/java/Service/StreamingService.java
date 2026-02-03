package Service;

import Repository.Movie.MySqlMovieRepository;

public class StreamingService {

    private final MySqlMovieRepository mRepo;

    public StreamingService(MySqlMovieRepository mRepo) {
        this.mRepo = mRepo;
    }

    public void movieCount(){
        mRepo.movieCount();
    }

}
